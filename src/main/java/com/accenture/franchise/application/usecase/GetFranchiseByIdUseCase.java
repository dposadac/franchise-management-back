package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.repository.FranquiciaRepository;
import com.accenture.franchise.domain.service.FranchiseValidationService;

public class GetFranchiseByIdUseCase {

    private final FranquiciaRepository franchiseRepository;
    private final FranchiseMapper franchiseMapper;
    private final FranchiseValidationService franchiseValidationService;

    public GetFranchiseByIdUseCase(
        FranquiciaRepository franchiseRepository, 
        FranchiseMapper franchiseMapper,
        FranchiseValidationService franchiseValidationService) {
        this.franchiseRepository = franchiseRepository;
        this.franchiseMapper = franchiseMapper;
        this.franchiseValidationService = franchiseValidationService;
    }

    public FranchiseResponse execute(String id) {

        franchiseValidationService.isUuidValidate(id);
        
        return franchiseRepository.findById(id)
                .map(franchiseMapper::toResponse)
                .orElseThrow(() -> new FranchiseNotFoundException(id));
    }
}
