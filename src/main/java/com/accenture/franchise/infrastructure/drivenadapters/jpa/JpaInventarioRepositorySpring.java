package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInventarioRepositorySpring extends JpaRepository<InventarioEntity, InventarioEntity.InventarioId> {}
