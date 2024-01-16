package com.osttra.orderservice.service;

import com.osttra.orderservice.dto.*;
import com.osttra.orderservice.entity.AccountAsset;
import com.osttra.orderservice.entity.Asset;
import com.osttra.orderservice.exception.AssetNotFoundException;
import com.osttra.orderservice.exception.InsufficientBalanceException;
import com.osttra.orderservice.exception.ValidationException;
import com.osttra.orderservice.repository.AccountAssetRepository;
import com.osttra.orderservice.repository.AssetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AssetService assetService;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private KafkaTemplate<String, OrderMessage> template;

    @Value("${order.topic}")
    private String orderTopic;

    @Value("${accountservice.base.url}")
    private String accountURL;

    @Value("${accountservice.addBalance.url}")
    private String accountAddBalanceURL;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AccountAssetRepository accountAssetRepository;

    @Override
    @Transactional
    public OrderMessage order(OrderDTO order, OrderType orderType) throws AssetNotFoundException, ValidationException, InsufficientBalanceException, AccountNotFoundException {
        log.info("Validating the order " + order + " for order type " + orderType);
        validate(order, orderType);

        log.info("Processing the order " + order + " for order type " + orderType);
        process(order, orderType);

        log.info("Sending the order message");
        OrderMessage orderMessage = OrderMessage.builder()
                .id(UUID.randomUUID().toString())
                .asset(assetService.getAssetById(order.getAssetId()))
                .account(getAccountDTO(order.getAccountId()))
                .oldAsset(orderType == OrderType.SWITCH ? assetService.getAssetById(order.getOldAssetId()) : null)
                .type(orderType)
                .status(OrderStatus.COMPLETED)
                .statusDetail("Order Submitted")
                .build();

        CompletableFuture<SendResult<String, OrderMessage>> future = template.send(orderTopic, orderMessage.getId(), orderMessage);

        future.whenComplete((stringOrderDTOSendResult, throwable) -> {
            if(throwable != null) {
                log.error("Order message sending failed", throwable);
            } else {
                log.info("Order message sent");
            }
        });

        return orderMessage;
    }

    private void validate(OrderDTO order, OrderType orderType) throws AssetNotFoundException, ValidationException, InsufficientBalanceException, AccountNotFoundException {
        assetService.validateAsset(order.getAssetId());
        if (orderType == OrderType.SWITCH) {
            if (Objects.isNull(order.getOldAssetId()) || order.getOldAssetId() == 0) {
                log.error("Old asset not selected for switch");
                throw new ValidationException("Old asset not selected for switch");
            } else {
                assetService.validateAsset(order.getOldAssetId());
                Asset asset = assetRepository.findById(order.getOldAssetId()).orElseThrow(() -> new AssetNotFoundException("Asset " + order.getOldAssetId() + " not found"));
                List<AccountAsset> accountAssets = accountAssetRepository.findByAccountIdAndAsset(order.getAccountId(), asset);
                if (accountAssets.isEmpty()) {
                    log.error("Old Asset " + order.getOldAssetId() + " not exist for account " + order.getAccountId());
                    throw new ValidationException("Old Asset " + order.getOldAssetId() + " not exist for account " + order.getAccountId());
                }
            }
        }
        validateAccount(order.getAccountId(), getPrice(order, orderType));
    }

    private void validateAccount(Integer accountId, Double price) throws InsufficientBalanceException, AccountNotFoundException {
        AccountDTO accountDTO = getAccountDTO(accountId);
        if (accountDTO.getBalance() < price) {
            log.error("Insufficient Balance");
            throw new InsufficientBalanceException("Insufficient Balance");
        }
    }

    private AccountDTO getAccountDTO(Integer accountId) throws AccountNotFoundException {
        AccountDTO accountDTO = restTemplate.getForObject(accountURL + accountId, AccountDTO.class);
        if (accountDTO == null) {
            log.error("Account " + accountId + " not found");
            throw new AccountNotFoundException("Account " + accountId + " not found");
        }
        return accountDTO;
    }

    private void process(OrderDTO order, OrderType orderType) throws AssetNotFoundException {
        AccountAsset accountAsset = new AccountAsset();
        accountAsset.setAccountId(order.getAccountId());
        accountAsset.setAsset(assetRepository.findById(order.getAssetId()).orElseThrow(() -> new AssetNotFoundException("Asset " + order.getAssetId() + " not found")));
        accountAsset.setStatus("ADDED");
        accountAssetRepository.save(accountAsset);

        if(orderType == OrderType.SWITCH) {
            Asset asset = assetRepository.findById(order.getOldAssetId()).orElseThrow(() -> new AssetNotFoundException("Asset " + order.getOldAssetId() + " not found"));
            List<AccountAsset> accountAssets = accountAssetRepository.findByAccountIdAndAsset(order.getAccountId(), asset);
            AccountAsset oldAccountAsset = accountAssets.get(0);
            oldAccountAsset.setStatus("REMOVED");
            accountAssetRepository.save(oldAccountAsset);
        }

        Double price = getPrice(order, orderType);
        DecimalFormat df = new DecimalFormat("#,###.##");
        String url = accountURL + accountAddBalanceURL.replace("{id}", order.getAccountId().toString()).replace("{amount}", df.format(price));
        restTemplate.put(url, null);
    }

    private Double getPrice(OrderDTO order, OrderType orderType) throws AssetNotFoundException {
        AssetDTO asset = assetService.getAssetById(order.getAssetId());
        Double price = asset.getPrice();
        if (orderType == OrderType.SWITCH) {
            if (Objects.nonNull(order.getOldAssetId()) && order.getOldAssetId() != 0) {
                AssetDTO oldAsset = assetService.getAssetById(order.getAssetId());
                price = price - oldAsset.getPrice();
            }
        } else if (orderType == OrderType.SELL) {
            price = 0.00 - price;
        }
        return price;
    }
}
