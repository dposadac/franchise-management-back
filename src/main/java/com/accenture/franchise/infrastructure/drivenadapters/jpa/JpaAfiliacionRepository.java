package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.Afiliacion;
import com.accenture.franchise.domain.repository.AfiliacionRepository;

import java.util.List;
import java.util.Optional;

public class JpaAfiliacionRepository implements AfiliacionRepository {

    private final JpaAfiliacionRepositorySpring springRepository;
    private final JpaSucursalRepositorySpring sucursalRepo;
    private final JpaFranquiciaRepositorySpring franquiciaRepo;

    public JpaAfiliacionRepository(JpaAfiliacionRepositorySpring springRepository,
                                    JpaSucursalRepositorySpring sucursalRepo,
                                    JpaFranquiciaRepositorySpring franquiciaRepo) {
        this.springRepository = springRepository;
        this.sucursalRepo = sucursalRepo;
        this.franquiciaRepo = franquiciaRepo;
    }

    @Override
    public Afiliacion save(Afiliacion afiliacion) {
        SucursalEntity sucursal = sucursalRepo.getReferenceById(afiliacion.getIdSucursal());
        FranquiciaEntity franquicia = franquiciaRepo.getReferenceById(afiliacion.getIdFranquicia());
        AfiliacionEntity entity = new AfiliacionEntity(sucursal, franquicia,
                afiliacion.getFechaCreacion(), afiliacion.getFechaActualizacion());
        return toDomain(springRepository.save(entity));
    }

    @Override
    public Optional<Afiliacion> findById(String idSucursal, String idFranquicia) {
        AfiliacionEntity.AfiliacionId key = new AfiliacionEntity.AfiliacionId(idSucursal, idFranquicia);
        return springRepository.findById(key).map(this::toDomain);
    }

    @Override
    public List<Afiliacion> findAll() {
        return springRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String idSucursal, String idFranquicia) {
        springRepository.deleteById(new AfiliacionEntity.AfiliacionId(idSucursal, idFranquicia));
    }

    private Afiliacion toDomain(AfiliacionEntity entity) {
        return new Afiliacion(
                entity.getId().getIdSucursal(),
                entity.getId().getIdFranquicia(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
