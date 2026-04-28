package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.BranchRequest;
import com.accenture.franchise.application.dto.BranchResponse;
import com.accenture.franchise.application.mapper.SucursalMapper;
import com.accenture.franchise.domain.model.Sucursal;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.domain.service.BranchValidationService;

public class CreateBranchUseCase {

    private final SucursalRepository sucursalRepository;
    private final BranchValidationService branchValidationService;
    private final SucursalMapper sucursalMapper;

    public CreateBranchUseCase(
        SucursalRepository sucursalRepository, 
        SucursalMapper sucursalMapper,
        BranchValidationService branchValidationService) {
        this.sucursalRepository = sucursalRepository;
        this.sucursalMapper = sucursalMapper;
        this.branchValidationService = branchValidationService;
    }

    public BranchResponse execute(BranchRequest request) {
        Sucursal sucursal = sucursalMapper.toDomain(request);
        
        branchValidationService.validate(sucursal);
        
        Sucursal saved = sucursalRepository.save(sucursal);
        return sucursalMapper.toResponse(saved);
    }
}
