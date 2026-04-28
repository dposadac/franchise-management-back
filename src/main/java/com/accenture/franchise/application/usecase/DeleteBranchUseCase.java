package com.accenture.franchise.application.usecase;

import com.accenture.franchise.domain.exception.BranchNotFoundException;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.domain.service.BranchValidationService;

import java.util.UUID;

public class DeleteBranchUseCase {

    private final SucursalRepository sucursalRepository;
    private final BranchValidationService branchValidationService;

    public DeleteBranchUseCase(SucursalRepository sucursalRepository, BranchValidationService branchValidationService) {
        this.sucursalRepository = sucursalRepository;
        this.branchValidationService = branchValidationService;
    }

    public void execute(UUID id) {

        branchValidationService.isUuidValidate(id.toString());
        
        sucursalRepository.findById(id.toString())
                .orElseThrow(() -> new BranchNotFoundException(id));
        sucursalRepository.deleteById(id.toString());
    }
}
