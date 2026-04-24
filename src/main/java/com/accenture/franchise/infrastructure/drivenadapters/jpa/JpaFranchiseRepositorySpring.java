package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaFranchiseRepositorySpring extends JpaRepository<FranchiseEntity, UUID> {}
