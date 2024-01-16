package com.osttra.orderservice.repository;

import com.osttra.orderservice.entity.AccountAsset;
import com.osttra.orderservice.entity.Asset;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountAssetRepository extends CrudRepository<AccountAsset, Integer> {

    List<AccountAsset> findByAccountIdAndAsset(Integer accountId, Asset asset);
}
