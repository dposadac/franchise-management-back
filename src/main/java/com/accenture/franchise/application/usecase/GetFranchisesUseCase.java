package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.repository.FranchiseRepository;

import java.util.List;

public class GetFranchisesUseCase {

    private final FranchiseRepository franchiseRepository;
    private final FranchiseMapper franchiseMapper;

    public GetFranchisesUseCase(FranchiseRepository franchiseRepository, FranchiseMapper franchiseMapper) {
        this.franchiseRepository = franchiseRepository;
        this.franchiseMapper = franchiseMapper;
    }

    public List<FranchiseResponse> execute() {
        return franchiseRepository.findAll()
                .stream()
                .map(franchiseMapper::toResponse)
                .toList();
    }
}
