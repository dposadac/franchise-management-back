package com.accenture.franchise.domain.repository;

import com.accenture.franchise.domain.model.Franquicia;

import java.util.List;
import java.util.Optional;

public interface FranquiciaRepository {
    Franquicia save(Franquicia franquicia);
    Optional<Franquicia> findById(String idFranquicia);
    List<Franquicia> findAll();
    void deleteById(String idFranquicia);
}
