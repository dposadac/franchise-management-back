package com.accenture.franchise.domain.repository;

import com.accenture.franchise.domain.model.Afiliacion;

import java.util.List;
import java.util.Optional;

public interface AfiliacionRepository {
    Afiliacion save(Afiliacion afiliacion);
    Optional<Afiliacion> findById(String idSucursal, String idFranquicia);
    List<Afiliacion> findAll();
    void deleteById(String idSucursal, String idFranquicia);
}
