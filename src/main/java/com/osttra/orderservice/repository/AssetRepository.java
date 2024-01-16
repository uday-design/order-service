package com.osttra.orderservice.repository;

import com.osttra.orderservice.entity.Asset;
import org.springframework.data.repository.CrudRepository;

public interface AssetRepository extends CrudRepository<Asset, Integer> {
}
