package com.accenture.franchise.domain.repository;

import com.accenture.franchise.domain.model.Inventario;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository {
    Inventario save(Inventario inventario);
    Optional<Inventario> findById(String idSucursal, String idProducto);
    List<Inventario> findAll();
    void deleteById(String idSucursal, String idProducto);
}
