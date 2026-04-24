package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseRequest;
import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.model.Franchise;
import com.accenture.franchise.domain.repository.FranchiseRepository;
import com.accenture.franchise.domain.service.FranchiseValidationService;

public class CreateFranchiseUseCase {

    private final FranchiseRepository franchiseRepository;
    private final FranchiseValidationService validationService;
    private final FranchiseMapper franchiseMapper;

    public CreateFranchiseUseCase(FranchiseRepository franchiseRepository,
                                   FranchiseValidationService validationService,
                                   FranchiseMapper franchiseMapper) {
        this.franchiseRepository = franchiseRepository;
        this.validationService = validationService;
        this.franchiseMapper = franchiseMapper;
    }

    public FranchiseResponse execute(FranchiseRequest request) {
        Franchise franchise = franchiseMapper.toDomain(request);
        validationService.validate(franchise);
        Franchise saved = franchiseRepository.save(franchise);
        return franchiseMapper.toResponse(saved);
    }
}
