package com.osttra.orderservice.service;

import com.osttra.orderservice.dto.*;
import com.osttra.orderservice.entity.AccountAsset;
import com.osttra.orderservice.entity.Asset;
import com.osttra.orderservice.exception.AssetNotFoundException;
import com.osttra.orderservice.exception.InsufficientBalanceException;
import com.osttra.orderservice.exception.ValidationException;
import com.osttra.orderservice.repository.AccountAssetRepository;
import com.osttra.orderservice.repository.AssetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private AssetService assetService;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private KafkaTemplate<String, OrderMessage> template;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AccountAssetRepository accountAssetRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testBuyAsset() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId("ABC");
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("Asset1");
        asset.setPrice(20.00);
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(1);
        assetDTO.setName("Asset1");
        assetDTO.setPrice(20.00);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setBalance(100.00);
        AccountAsset accountAsset = new AccountAsset();
        accountAsset.setId(1);
        doNothing().when(assetService).validateAsset(anyInt());
        when(assetService.getAssetById(anyInt())).thenReturn(assetDTO);
        when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(accountDTO);
        when(assetRepository.findById(anyInt())).thenReturn(Optional.of(asset));
        when(accountAssetRepository.save(any(AccountAsset.class))).thenReturn(accountAsset);
        doNothing().when(restTemplate).put(anyString(), any());
        ReflectionTestUtils.setField(orderService, "accountAddBalanceURL", "value");
        ReflectionTestUtils.setField(orderService, "orderTopic", "value");
        when(template.send(anyString(), anyString(), any(OrderMessage.class))).thenReturn(CompletableFuture.completedFuture(null));
        OrderDTO order = new OrderDTO();
        order.setAssetId(1);
        order.setAccountId(1);
        order.setOldAssetId(0);
        OrderMessage om = orderService.order(order, OrderType.BUY);
        Assertions.assertEquals(1, om.getAsset().getId());
    }

    @Test
    public void testSellAsset() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId("ABC");
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("Asset1");
        asset.setPrice(20.00);
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(1);
        assetDTO.setName("Asset1");
        assetDTO.setPrice(20.00);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setBalance(100.00);
        AccountAsset accountAsset = new AccountAsset();
        accountAsset.setId(1);
        doNothing().when(assetService).validateAsset(anyInt());
        when(assetService.getAssetById(anyInt())).thenReturn(assetDTO);
        when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(accountDTO);
        when(assetRepository.findById(anyInt())).thenReturn(Optional.of(asset));
        when(accountAssetRepository.save(any(AccountAsset.class))).thenReturn(accountAsset);
        doNothing().when(restTemplate).put(anyString(), any());
        ReflectionTestUtils.setField(orderService, "accountAddBalanceURL", "value");
        ReflectionTestUtils.setField(orderService, "orderTopic", "value");
        when(template.send(anyString(), anyString(), any(OrderMessage.class))).thenReturn(CompletableFuture.completedFuture(null));
        OrderDTO order = new OrderDTO();
        order.setAssetId(1);
        order.setAccountId(1);
        order.setOldAssetId(0);
        OrderMessage om = orderService.order(order, OrderType.SELL);
        Assertions.assertEquals(1, om.getAsset().getId());
    }

    @Test
    public void testSwitchAsset() throws ValidationException, InsufficientBalanceException, AssetNotFoundException, AccountNotFoundException {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId("ABC");
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("Asset1");
        asset.setPrice(20.00);
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(1);
        assetDTO.setName("Asset1");
        assetDTO.setPrice(20.00);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setBalance(100.00);
        AccountAsset accountAsset = new AccountAsset();
        accountAsset.setId(1);
        accountAsset.setAsset(asset);
        doNothing().when(assetService).validateAsset(anyInt());
        when(assetService.getAssetById(anyInt())).thenReturn(assetDTO);
        when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(accountDTO);
        when(assetRepository.findById(anyInt())).thenReturn(Optional.of(asset));
        when(accountAssetRepository.findByAccountIdAndAsset(anyInt(), any(Asset.class))).thenReturn(List.of(accountAsset));
        when(accountAssetRepository.save(any(AccountAsset.class))).thenReturn(accountAsset);
        doNothing().when(restTemplate).put(anyString(), any());
        ReflectionTestUtils.setField(orderService, "accountAddBalanceURL", "value");
        ReflectionTestUtils.setField(orderService, "orderTopic", "value");
        when(template.send(anyString(), anyString(), any(OrderMessage.class))).thenReturn(CompletableFuture.completedFuture(null));
        OrderDTO order = new OrderDTO();
        order.setAssetId(1);
        order.setAccountId(1);
        order.setOldAssetId(1);
        OrderMessage om = orderService.order(order, OrderType.SWITCH);
        Assertions.assertEquals(1, om.getAsset().getId());
    }

    @Test
    public void testBuyAssetVException() throws AssetNotFoundException {
        doNothing().when(assetService).validateAsset(anyInt());
        OrderDTO order = new OrderDTO();
        order.setAssetId(1);
        order.setAccountId(1);
        order.setOldAssetId(0);
        ValidationException thrown = assertThrows(
                ValidationException.class,
                () -> orderService.order(order, OrderType.SWITCH),
                "Expected buyAsset() to throw, but it didn't"
        );
    }

}
