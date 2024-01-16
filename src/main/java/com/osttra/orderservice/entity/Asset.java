package com.osttra.orderservice.entity;

import com.osttra.orderservice.dto.AssetDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "asset")
@Getter
@Setter
@NoArgsConstructor
public class Asset {
    @Id
    private Integer id;

    private String name;

    private Double price;

    public Asset(AssetDTO assetDTO) {
        id = assetDTO.getId();
        name = assetDTO.getName();
        price = assetDTO.getPrice();
    }
}
