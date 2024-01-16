package com.osttra.orderservice.service;

import com.osttra.orderservice.dto.AssetDTO;
import com.osttra.orderservice.entity.Asset;
import com.osttra.orderservice.exception.AssetAlreadyExistException;
import com.osttra.orderservice.exception.AssetNotFoundException;
import com.osttra.orderservice.repository.AssetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Override
    @Transactional
    public AssetDTO createAsset(AssetDTO asset) throws AssetAlreadyExistException {
        Optional<Asset> asset1 = assetRepository.findById(asset.getId());
        if (asset1.isPresent()) {
            log.error("Asset " + asset1.get().getId() + " already exist!");
            throw new AssetAlreadyExistException("Asset " + asset1.get().getId() + " already exist!");
        }
        return new AssetDTO(assetRepository.save(new Asset(asset)));
    }

    @Override
    public AssetDTO getAssetById(Integer id) throws AssetNotFoundException {
        return new AssetDTO(assetRepository.findById(id)
                .orElseThrow(() -> new AssetNotFoundException("Asset " + id + " not found!")));
    }

    @Override
    public List<AssetDTO> getAllAsset() {
        List<AssetDTO> assetDTOS = new LinkedList<>();
        assetRepository.findAll().forEach(asset -> assetDTOS.add(new AssetDTO(asset)));
        return assetDTOS;
    }

    @Override
    public void validateAsset(Integer assetId) throws AssetNotFoundException {
        getAssetById(assetId);
    }

}
