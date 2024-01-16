package com.osttra.orderservice.controller;

import com.osttra.orderservice.dto.AssetDTO;
import com.osttra.orderservice.exception.AssetAlreadyExistException;
import com.osttra.orderservice.exception.AssetNotFoundException;
import com.osttra.orderservice.service.AssetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AssetControllerTest {

    @Mock
    private AssetService assetService;

    @InjectMocks
    private AssetController assetController;

    @Test
    public void testGetAsset() throws AssetNotFoundException {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(1);
        assetDTO.setName("Asset1");
        assetDTO.setPrice(20.00);
        Mockito.when(assetService.getAssetById(Mockito.anyInt())).thenReturn(assetDTO);
        AssetDTO asset = assetController.getAsset(1);
        Assertions.assertEquals(1, asset.getId());
    }

    @Test
    public void testGetAssetException() throws AssetNotFoundException {
        Mockito.when(assetService.getAssetById(Mockito.anyInt())).thenThrow(AssetNotFoundException.class);
        AssetNotFoundException thrown = assertThrows(
                AssetNotFoundException.class,
                () -> assetController.getAsset(1),
                "Expected getAsset() to throw, but it didn't"
        );
    }

    @Test
    public void testGetAllAsset() {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(1);
        assetDTO.setName("Asset1");
        assetDTO.setPrice(20.00);
        List<AssetDTO> assetDTOS = new ArrayList<>();
        assetDTOS.add(assetDTO);
        Mockito.when(assetService.getAllAsset()).thenReturn(assetDTOS);
        List<AssetDTO> assets = assetController.getAllAssets();
        Assertions.assertEquals(1, assets.size());
    }

    @Test
    public void testCreateAsset() throws AssetAlreadyExistException {
        AssetDTO assetDTO = new AssetDTO();
        assetDTO.setId(1);
        assetDTO.setName("Asset1");
        assetDTO.setPrice(20.00);
        Mockito.when(assetService.createAsset(Mockito.any(AssetDTO.class))).thenReturn(assetDTO);
        AssetDTO asset = assetController.createAsset(assetDTO);
        Assertions.assertEquals(1, asset.getId());
    }

    @Test
    public void testCreateAssetException() throws AssetAlreadyExistException {
        Mockito.when(assetService.createAsset(Mockito.any(AssetDTO.class))).thenThrow(AssetAlreadyExistException.class);
        AssetAlreadyExistException thrown = assertThrows(
                AssetAlreadyExistException.class,
                () -> assetController.createAsset(new AssetDTO()),
                "Expected createAsset() to throw, but it didn't"
        );
    }
}
