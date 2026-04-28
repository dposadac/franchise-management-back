package com.accenture.franchise.domain.repository;

import com.accenture.franchise.domain.model.TopProductByBranch;

import java.util.List;

public interface TopProductRepository {
    List<TopProductByBranch> findTopProductsByFranchiseAndBranch(String idFranquicia, String idSucursal);
}
