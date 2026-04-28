package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.EstadoPago;
import com.accenture.franchise.domain.repository.EstadoPagoRepository;

import java.util.List;
import java.util.Optional;

public class JpaEstadoPagoRepository implements EstadoPagoRepository {

    private final JpaEstadoPagoRepositorySpring springRepository;

    public JpaEstadoPagoRepository(JpaEstadoPagoRepositorySpring springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public EstadoPago save(EstadoPago estadoPago) {
        return toDomain(springRepository.save(toEntity(estadoPago)));
    }

    @Override
    public Optional<EstadoPago> findById(String idEstado) {
        return springRepository.findById(idEstado).map(this::toDomain);
    }

    @Override
    public List<EstadoPago> findAll() {
        return springRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String idEstado) {
        springRepository.deleteById(idEstado);
    }

    private EstadoPagoEntity toEntity(EstadoPago estadoPago) {
        return new EstadoPagoEntity(estadoPago.getIdEstado(), estadoPago.getNombre());
    }

    private EstadoPago toDomain(EstadoPagoEntity entity) {
        return new EstadoPago(entity.getIdEstado(), entity.getNombre());
    }
}
