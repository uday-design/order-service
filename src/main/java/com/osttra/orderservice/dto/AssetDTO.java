package com.osttra.orderservice.dto;

import com.osttra.orderservice.entity.Asset;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AssetDTO {

    private Integer id;

    private String name;

    private Double price;

    public AssetDTO(Asset asset) {
        id = asset.getId();
        name = asset.getName();
        price = asset.getPrice();
    }
}
