package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaTopProductRepositorySpring extends JpaRepository<AfiliacionEntity, AfiliacionEntity.AfiliacionId>,
        JpaSpecificationExecutor<AfiliacionEntity> {
}
