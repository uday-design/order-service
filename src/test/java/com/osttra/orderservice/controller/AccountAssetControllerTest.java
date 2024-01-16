package com.osttra.orderservice.controller;

import com.osttra.orderservice.dto.AccountAssetDTO;
import com.osttra.orderservice.service.AccountAssetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountAssetControllerTest {

    @Mock
    private AccountAssetService accountAssetService;

    @InjectMocks
    private AccountAssetController accountAssetController;

    @Test
    public void testGetAllAssets() {
        AccountAssetDTO accountAssetDTO = new AccountAssetDTO();
        List<AccountAssetDTO> accountAssetDTOS = new ArrayList<>();
        accountAssetDTOS.add(accountAssetDTO);
        when(accountAssetService.getAll()).thenReturn(accountAssetDTOS);
        List<AccountAssetDTO> accountAssets = accountAssetController.getAllAssets();
        assertEquals(1, accountAssets.size());
    }
}
