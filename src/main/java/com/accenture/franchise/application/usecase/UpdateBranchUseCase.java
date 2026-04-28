package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.BranchResponse;
import com.accenture.franchise.application.dto.UpdateBranchRequest;
import com.accenture.franchise.application.mapper.SucursalMapper;
import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.model.Sucursal;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.domain.service.BranchValidationService;
import com.accenture.franchise.infrastructure.shared.DateUtils;

public class UpdateBranchUseCase {
    
    private final SucursalRepository branchRepository;
    private final SucursalMapper branchMapper;
    private final BranchValidationService branchValidationService;

    public UpdateBranchUseCase(SucursalRepository branchRepository,
            SucursalMapper branchMapper,
            BranchValidationService branchValidationService) {
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper;
        this.branchValidationService = branchValidationService;
    }

    public BranchResponse execute(String id, UpdateBranchRequest request) {

        branchValidationService.isUuidValidate(id);

        Sucursal existing = branchRepository.findById(id)
                .orElseThrow(() -> new FranchiseNotFoundException(id));

        Sucursal updated = new Sucursal(
                existing.getIdSucursal(),
                request.name(),
                existing.getFechaCreacion(),
                DateUtils.nowBogota()
        );

        branchValidationService.validate(updated);       
        Sucursal saved = branchRepository.save(updated);
        return branchMapper.toResponse(saved);
    }
}
