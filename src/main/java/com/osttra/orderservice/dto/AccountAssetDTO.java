package com.osttra.orderservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountAssetDTO {

    private AccountDTO account;

    private AssetDTO asset;

    private String status;
}
