package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.InventoryRequest;
import com.accenture.franchise.application.dto.Response;
import com.accenture.franchise.application.mapper.MasterMapper;
import com.accenture.franchise.domain.model.Inventario;
import com.accenture.franchise.domain.repository.InventarioRepository;
import com.accenture.franchise.domain.repository.ProductoRepository;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.infrastructure.shared.DateUtils;
import com.accenture.franchise.infrastructure.shared.Functions;

public class AddProductToBranchUseCase {

    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;
    private final MasterMapper masterMapper;

    public AddProductToBranchUseCase(
        SucursalRepository sucursalRepository,
        ProductoRepository productoRepository,
        InventarioRepository inventarioRepository,
        MasterMapper mapper
    ) {
        this.sucursalRepository = sucursalRepository;
        this.productoRepository = productoRepository;
        this.inventarioRepository = inventarioRepository;
        this.masterMapper = mapper;
    }
    
    public Response execute(InventoryRequest request) {
        Functions.isUuidValidate(request.branchId());
        Functions.isUuidValidate(request.productId());

        productoRepository.findById(request.productId())
            .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + request.productId()));

        sucursalRepository.findById(request.branchId())
            .orElseThrow(() -> new IllegalArgumentException("Branch not found with id: " + request.branchId()));

        Inventario inventario = new Inventario(
            request.branchId(),
            request.productId(),
            request.stockQuantity(),
            request.statusId(),
            DateUtils.nowBogota(),
            null
        );

        inventarioRepository.save(inventario);

        return masterMapper.toResponse("Product added to branch successfully", true, null);
    }
}
