package com.osttra.orderservice.service;

import com.osttra.orderservice.dto.AssetDTO;
import com.osttra.orderservice.entity.Asset;
import com.osttra.orderservice.exception.AssetAlreadyExistException;
import com.osttra.orderservice.exception.AssetNotFoundException;
import com.osttra.orderservice.repository.AssetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetServiceImpl assetService;

    @Test
    public void testGetAsset() throws AssetNotFoundException {
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("Asset1");
        asset.setPrice(20.00);
        Mockito.when(assetRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(asset));
        AssetDTO assetDTO = assetService.getAssetById(1);
        Assertions.assertEquals(1, assetDTO.getId());
    }

    @Test
    public void testGetAssetException() throws AssetNotFoundException {
        Mockito.when(assetRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        AssetNotFoundException thrown = assertThrows(
                AssetNotFoundException.class,
                () -> assetService.getAssetById(1),
                "Expected getAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testGetAllAsset() {
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("Asset1");
        asset.setPrice(20.00);
        List<Asset> assets = new ArrayList<>();
        assets.add(asset);
        Mockito.when(assetRepository.findAll()).thenReturn(assets);
        List<AssetDTO> assetDTOS = assetService.getAllAsset();
        Assertions.assertEquals(1, assetDTOS.size());
    }

    @Test
    public void testCreateAsset() throws AssetAlreadyExistException {
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("Asset1");
        asset.setPrice(20.00);
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(1);
        Mockito.when(assetRepository.save(Mockito.any(Asset.class))).thenReturn(asset);
        AssetDTO assetDto = assetService.createAsset(assetDTO);
        Assertions.assertEquals(1, assetDto.getId());
    }

    @Test
    public void testCreateAssetException() {
        Asset asset = new Asset();
        asset.setId(1);
        asset.setName("Asset1");
        asset.setPrice(20.00);
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(1);
        Mockito.when(assetRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(asset));
        AssetAlreadyExistException thrown = assertThrows(
                AssetAlreadyExistException.class,
                () -> assetService.createAsset(assetDTO),
                "Expected createAsset() to throw, but it didn't"
        );
    }


}
