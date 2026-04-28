package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.BranchResponse;
import com.accenture.franchise.application.mapper.SucursalMapper;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.domain.service.BranchValidationService;

import java.util.List;

public class GetBranchUseCase {

    private final SucursalRepository sucursalRepository;
    private final SucursalMapper sucursalMapper;

    public GetBranchUseCase(
        SucursalRepository sucursalRepository,
        SucursalMapper sucursalMapper) {
        this.sucursalRepository = sucursalRepository;
        this.sucursalMapper = sucursalMapper;
    }

    public List<BranchResponse> execute() {
        return sucursalRepository.findAll()
                .stream()
                .map(sucursalMapper::toResponse)
                .toList();
    }
}
