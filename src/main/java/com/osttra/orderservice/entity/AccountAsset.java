package com.osttra.orderservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "account_asset")
@Getter
@Setter
public class AccountAsset {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    @OneToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private String status;
}
