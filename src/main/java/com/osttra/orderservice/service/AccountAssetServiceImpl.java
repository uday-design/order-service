package com.osttra.orderservice.service;

import com.osttra.orderservice.dto.AccountAssetDTO;
import com.osttra.orderservice.dto.AccountDTO;
import com.osttra.orderservice.dto.AssetDTO;
import com.osttra.orderservice.repository.AccountAssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Service
public class AccountAssetServiceImpl implements AccountAssetService {

    @Autowired
    private AccountAssetRepository accountAssetRepository;

    @Value("${accountservice.base.url}")
    private String accountURL;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<AccountAssetDTO> getAll() {
        List<AccountAssetDTO> accountAssetDTOS = new LinkedList<>();
        accountAssetRepository.findAll().forEach(accountAsset -> {
            AccountDTO accountDTO = restTemplate.getForObject(accountURL + accountAsset.getAccountId(), AccountDTO.class);
            accountAssetDTOS.add(AccountAssetDTO.builder()
                    .account(accountDTO)
                    .asset(new AssetDTO(accountAsset.getAsset()))
                    .status(accountAsset.getStatus())
                    .build());
        });
        return accountAssetDTOS;
    }

}
