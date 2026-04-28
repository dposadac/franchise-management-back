package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.DeleteInventoryRequest;
import com.accenture.franchise.application.dto.Response;
import com.accenture.franchise.application.mapper.MasterMapper;
import com.accenture.franchise.domain.exception.BranchNotFoundException;
import com.accenture.franchise.domain.exception.ProductNotFoundException;
import com.accenture.franchise.domain.repository.InventarioRepository;
import com.accenture.franchise.domain.repository.ProductoRepository;
import com.accenture.franchise.domain.repository.SucursalRepository;

import java.util.UUID;

public class DeleteProductFromBranchUseCase {
    
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    private final InventarioRepository inventarioRepository;
    private final MasterMapper masterMapper;

    public DeleteProductFromBranchUseCase(
        ProductoRepository productoRepository,
        SucursalRepository sucursalRepository,
        InventarioRepository inventarioRepository,
        MasterMapper mapper
    ) {
        this.productoRepository = productoRepository;
        this.sucursalRepository = sucursalRepository;
        this.inventarioRepository = inventarioRepository;
        this.masterMapper = mapper;
    }
    
    public Response execute(DeleteInventoryRequest request) {
        productoRepository.findById(request.productId())
                .orElseThrow(() -> new ProductNotFoundException(UUID.fromString(request.productId())));

        sucursalRepository.findById(request.branchId())
                .orElseThrow(() -> new BranchNotFoundException(request.branchId()));

        inventarioRepository.deleteById(request.branchId(), request.productId());

        return masterMapper.toResponse("Product removed from branch successfully", true, null);
    }
}
