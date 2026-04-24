package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.repository.FranchiseRepository;

import java.util.UUID;

public class GetFranchiseByIdUseCase {

    private final FranchiseRepository franchiseRepository;
    private final FranchiseMapper franchiseMapper;

    public GetFranchiseByIdUseCase(FranchiseRepository franchiseRepository, FranchiseMapper franchiseMapper) {
        this.franchiseRepository = franchiseRepository;
        this.franchiseMapper = franchiseMapper;
    }

    public FranchiseResponse execute(UUID id) {
        return franchiseRepository.findById(id)
                .map(franchiseMapper::toResponse)
                .orElseThrow(() -> new FranchiseNotFoundException(id));
    }
}
