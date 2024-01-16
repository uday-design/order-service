package com.osttra.orderservice.controller;

import com.osttra.orderservice.dto.AccountAssetDTO;
import com.osttra.orderservice.service.AccountAssetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/account-asset")
public class AccountAssetController {

    @Autowired
    private AccountAssetService accountAssetService;

    @GetMapping()
    public List<AccountAssetDTO> getAllAssets() {
        log.info("Retrieving all account assets");
        return accountAssetService.getAll();
    }

}
