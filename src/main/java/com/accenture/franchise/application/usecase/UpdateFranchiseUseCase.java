package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.dto.UpdateFranchiseRequest;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.model.Franchise;
import com.accenture.franchise.domain.repository.FranchiseRepository;
import com.accenture.franchise.domain.service.FranchiseValidationService;

import java.util.UUID;

public class UpdateFranchiseUseCase {

    private final FranchiseRepository franchiseRepository;
    private final FranchiseValidationService validationService;
    private final FranchiseMapper franchiseMapper;

    public UpdateFranchiseUseCase(FranchiseRepository franchiseRepository,
                                   FranchiseValidationService validationService,
                                   FranchiseMapper franchiseMapper) {
        this.franchiseRepository = franchiseRepository;
        this.validationService = validationService;
        this.franchiseMapper = franchiseMapper;
    }

    public FranchiseResponse execute(UUID id, UpdateFranchiseRequest request) {
        Franchise existing = franchiseRepository.findById(id)
                .orElseThrow(() -> new FranchiseNotFoundException(id));

        Franchise updated = new Franchise(
                existing.getId(),
                request.name(),
                request.address(),
                request.phone(),
                request.email(),
                existing.getStatus()
        );

        validationService.validate(updated);
        Franchise saved = franchiseRepository.save(updated);
        return franchiseMapper.toResponse(saved);
    }
}
