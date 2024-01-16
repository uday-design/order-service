package com.osttra.orderservice.service;

import com.osttra.orderservice.dto.AssetDTO;
import com.osttra.orderservice.exception.AssetAlreadyExistException;
import com.osttra.orderservice.exception.AssetNotFoundException;

import java.util.List;

public interface AssetService {
    AssetDTO createAsset(AssetDTO asset) throws AssetAlreadyExistException;

    AssetDTO getAssetById(Integer id) throws AssetNotFoundException;

    List<AssetDTO> getAllAsset();

    void validateAsset(Integer assetId) throws AssetNotFoundException;
}
