package com.osttra.orderservice.service;

import com.osttra.orderservice.dto.AccountAssetDTO;
import com.osttra.orderservice.dto.AccountDTO;
import com.osttra.orderservice.entity.AccountAsset;
import com.osttra.orderservice.entity.Asset;
import com.osttra.orderservice.repository.AccountAssetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountAssetServiceTest {

    @Mock
    private AccountAssetRepository accountAssetRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AccountAssetServiceImpl accountAssetService;

    @Test
    public void testGetAllAssets() {
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("Asset1");
        asset.setPrice(20.00);
        AccountAsset accountAssetDTO = new AccountAsset();
        accountAssetDTO.setAccountId(1);
        accountAssetDTO.setAsset(asset);
        accountAssetDTO.setStatus("ADDED");
        List<AccountAsset> accountAssets = new ArrayList<>();
        accountAssets.add(accountAssetDTO);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setBalance(100.00);
        when(accountAssetRepository.findAll()).thenReturn(accountAssets);
        when(restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(accountDTO);
        List<AccountAssetDTO> accountAssetDTOS = accountAssetService.getAll();
        assertEquals(1, accountAssetDTOS.size());
    }

}
