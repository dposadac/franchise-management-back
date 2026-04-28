package com.accenture.franchise.domain.repository;

import com.accenture.franchise.domain.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    Producto save(Producto producto);
    Optional<Producto> findById(String idProducto);
    List<Producto> findAll();
    void deleteById(String idProducto);
}
