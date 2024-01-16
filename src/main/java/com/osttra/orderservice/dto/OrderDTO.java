package com.osttra.orderservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO {

    private Integer accountId;

    private Integer assetId;

    private Integer oldAssetId;
}
