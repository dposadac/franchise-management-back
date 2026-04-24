package com.accenture.franchise.application.mapper;

import com.accenture.franchise.application.dto.FranchiseRequest;
import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.domain.model.Franchise;
import com.accenture.franchise.domain.model.FranchiseStatus;

import java.util.UUID;

public class FranchiseMapper {

    public Franchise toDomain(FranchiseRequest request) {
        return new Franchise(
                UUID.randomUUID(),
                request.name(),
                request.address(),
                request.phone(),
                request.email(),
                FranchiseStatus.ACTIVE
        );
    }

    public FranchiseResponse toResponse(Franchise franchise) {
        return new FranchiseResponse(
                franchise.getId(),
                franchise.getName(),
                franchise.getAddress(),
                franchise.getPhone(),
                franchise.getEmail(),
                franchise.getStatus()
        );
    }
}
