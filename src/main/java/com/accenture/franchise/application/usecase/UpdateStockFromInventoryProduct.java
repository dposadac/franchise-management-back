package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.Response;
import com.accenture.franchise.application.dto.UpdateInventoryRequest;
import com.accenture.franchise.application.mapper.MasterMapper;
import com.accenture.franchise.domain.exception.BranchNotFoundException;
import com.accenture.franchise.domain.exception.InventoryNotFoundException;
import com.accenture.franchise.domain.exception.ProductNotFoundException;
import com.accenture.franchise.domain.model.Inventario;
import com.accenture.franchise.domain.repository.InventarioRepository;
import com.accenture.franchise.domain.repository.ProductoRepository;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.infrastructure.shared.DateUtils;

import java.util.UUID;

public class UpdateStockFromInventoryProduct {
    
    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;
    private final MasterMapper masterMapper;

    public UpdateStockFromInventoryProduct(
        SucursalRepository sucursalRepository,
        ProductoRepository productoRepository,
        InventarioRepository inventarioRepository,
        MasterMapper mapper
    ) {
        this.productoRepository = productoRepository;
        this.sucursalRepository = sucursalRepository;
        this.inventarioRepository = inventarioRepository;
        this.masterMapper = mapper;
    }
    
    public Response execute(UpdateInventoryRequest request) {
        productoRepository.findById(request.productId())
                .orElseThrow(() -> new ProductNotFoundException(UUID.fromString(request.productId())));

        sucursalRepository.findById(request.branchId())
                .orElseThrow(() -> new BranchNotFoundException(request.branchId()));

        Inventario inventario = inventarioRepository.findById(request.branchId(), request.productId())
                .orElseThrow(() -> new InventoryNotFoundException(request.branchId(), request.productId()));

        inventario.setCantidadStock(request.stockQuantity());
        inventario.setFechaActualizacion(DateUtils.nowBogota());

        Inventario updated = inventarioRepository.save(inventario);

        return masterMapper.toResponse("Stock updated successfully", true, updated);
    }
}
