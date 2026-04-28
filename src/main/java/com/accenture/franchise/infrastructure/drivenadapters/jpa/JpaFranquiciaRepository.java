package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.Franquicia;
import com.accenture.franchise.domain.repository.FranquiciaRepository;

import java.util.List;
import java.util.Optional;

public class JpaFranquiciaRepository implements FranquiciaRepository {

    private final JpaFranquiciaRepositorySpring springRepository;

    public JpaFranquiciaRepository(JpaFranquiciaRepositorySpring springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Franquicia save(Franquicia franquicia) {
        return toDomain(springRepository.save(toEntity(franquicia)));
    }

    @Override
    public Optional<Franquicia> findById(String idFranquicia) {
        return springRepository.findById(idFranquicia).map(this::toDomain);
    }

    @Override
    public List<Franquicia> findAll() {
        return springRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String idFranquicia) {
        springRepository.deleteById(idFranquicia);
    }

    private FranquiciaEntity toEntity(Franquicia franquicia) {
        return new FranquiciaEntity(
                franquicia.getIdFranquicia(),
                franquicia.getNombre(),
                franquicia.getFechaCreacion(),
                franquicia.getFechaActualizacion()
        );
    }

    private Franquicia toDomain(FranquiciaEntity entity) {
        return new Franquicia(
                entity.getIdFranquicia(),
                entity.getNombre(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
