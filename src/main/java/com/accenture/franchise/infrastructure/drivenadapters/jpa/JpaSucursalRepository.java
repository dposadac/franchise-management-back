package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.Sucursal;
import com.accenture.franchise.domain.repository.SucursalRepository;

import java.util.List;
import java.util.Optional;

public class JpaSucursalRepository implements SucursalRepository {

    private final JpaSucursalRepositorySpring springRepository;

    public JpaSucursalRepository(JpaSucursalRepositorySpring springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Sucursal save(Sucursal sucursal) {
        return toDomain(springRepository.save(toEntity(sucursal)));
    }

    @Override
    public Optional<Sucursal> findById(String idSucursal) {
        return springRepository.findById(idSucursal).map(this::toDomain);
    }

    @Override
    public List<Sucursal> findAll() {
        return springRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String idSucursal) {
        springRepository.deleteById(idSucursal);
    }

    private SucursalEntity toEntity(Sucursal sucursal) {
        return new SucursalEntity(
                sucursal.getIdSucursal(),
                sucursal.getNombre(),
                sucursal.getFechaCreacion(),
                sucursal.getFechaActualizacion()
        );
    }

    private Sucursal toDomain(SucursalEntity entity) {
        return new Sucursal(
                entity.getIdSucursal(),
                entity.getNombre(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
