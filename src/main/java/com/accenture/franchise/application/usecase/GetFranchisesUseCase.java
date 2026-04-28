package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.repository.FranquiciaRepository;

import java.util.List;

public class GetFranchisesUseCase {

    private final FranquiciaRepository franchiseRepository;
    private final FranchiseMapper franchiseMapper;

    public GetFranchisesUseCase(FranquiciaRepository franchiseRepository, FranchiseMapper franchiseMapper) {
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
