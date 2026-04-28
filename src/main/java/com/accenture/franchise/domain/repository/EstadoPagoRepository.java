package com.accenture.franchise.domain.repository;

import com.accenture.franchise.domain.model.EstadoPago;

import java.util.List;
import java.util.Optional;

public interface EstadoPagoRepository {
    EstadoPago save(EstadoPago estadoPago);
    Optional<EstadoPago> findById(String idEstado);
    List<EstadoPago> findAll();
    void deleteById(String idEstado);
}
