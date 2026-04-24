package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.Franchise;
import com.accenture.franchise.domain.repository.FranchiseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JpaFranchiseRepository implements FranchiseRepository {

    private final JpaFranchiseRepositorySpring springRepository;

    public JpaFranchiseRepository(JpaFranchiseRepositorySpring springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Franchise save(Franchise franchise) {
        FranchiseEntity entity = toEntity(franchise);
        FranchiseEntity saved = springRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Franchise> findById(UUID id) {
        return springRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Franchise> findAll() {
        return springRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        springRepository.deleteById(id);
    }

    private FranchiseEntity toEntity(Franchise franchise) {
        return new FranchiseEntity(
                franchise.getId(),
                franchise.getName(),
                franchise.getAddress(),
                franchise.getPhone(),
                franchise.getEmail(),
                franchise.getStatus()
        );
    }

    private Franchise toDomain(FranchiseEntity entity) {
        return new Franchise(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getStatus()
        );
    }
}
