package com.osttra.orderservice.controller;

import com.osttra.orderservice.dto.AssetDTO;
import com.osttra.orderservice.exception.AssetAlreadyExistException;
import com.osttra.orderservice.exception.AssetNotFoundException;
import com.osttra.orderservice.service.AssetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping(path = "/{id}")
    public AssetDTO getAsset(@PathVariable Integer id) throws AssetNotFoundException {
        log.info("Retrieving asset by id " + id);
        return assetService.getAssetById(id);
    }

    @GetMapping()
    public List<AssetDTO> getAllAssets() {
        log.info("Retrieving all assets");
        return assetService.getAllAsset();
    }

    @PostMapping
    public AssetDTO createAsset(@RequestBody AssetDTO asset) throws AssetAlreadyExistException {
        log.info("Creating asset " + asset);
        return assetService.createAsset(asset);
    }

}
