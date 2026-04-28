package com.accenture.franchise.application.usecase;

import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.repository.FranquiciaRepository;
import com.accenture.franchise.domain.service.FranchiseValidationService;

public class DeleteFranchiseUseCase {

    private final FranquiciaRepository franchiseRepository;
    private final FranchiseValidationService franchiseValidationService;

    public DeleteFranchiseUseCase(FranquiciaRepository franchiseRepository, FranchiseValidationService franchiseValidationService) {
        this.franchiseRepository = franchiseRepository;
        this.franchiseValidationService = franchiseValidationService;
    }

    public void execute(String id) {

        franchiseValidationService.isUuidValidate(id);
        
        franchiseRepository.findById(id)
                .orElseThrow(() -> new FranchiseNotFoundException(id));
        franchiseRepository.deleteById(id);
    }
}
