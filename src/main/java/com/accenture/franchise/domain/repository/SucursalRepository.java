package com.accenture.franchise.domain.repository;

import com.accenture.franchise.domain.model.Sucursal;

import java.util.List;
import java.util.Optional;

public interface SucursalRepository {
    Sucursal save(Sucursal sucursal);
    Optional<Sucursal> findById(String idSucursal);
    List<Sucursal> findAll();
    void deleteById(String idSucursal);
}
