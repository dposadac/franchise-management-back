package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.BranchResponse;
import com.accenture.franchise.application.mapper.SucursalMapper;
import com.accenture.franchise.domain.exception.BranchNotFoundException;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.domain.service.BranchValidationService;

import java.util.UUID;

public class GetBranchByIdUseCase {

    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;
    private final BranchValidationService branchValidationService;

    public GetBranchByIdUseCase(
        SucursalRepository sucursalRepository, 
        SucursalMapper sucursalMapper,
        BranchValidationService branchValidationService) {
        this.sucursalRepository = sucursalRepository;
        this.sucursalMapper = sucursalMapper;
        this.branchValidationService = branchValidationService;
    }

    public BranchResponse execute(UUID id) {

        branchValidationService.isUuidValidate(id.toString());
        
        return sucursalRepository.findById(id.toString())
                .map(sucursalMapper::toResponse)
                .orElseThrow(() -> new BranchNotFoundException(id));
    }
}
