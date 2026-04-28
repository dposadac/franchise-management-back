package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.ProductResponse;
import com.accenture.franchise.application.mapper.ProductoMapper;
import com.accenture.franchise.domain.exception.ProductNotFoundException;
import com.accenture.franchise.domain.repository.ProductoRepository;
import com.accenture.franchise.domain.service.ProductValidationService;

import java.util.UUID;

public class GetProductByIdUseCase {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final ProductValidationService productValidationService;

    public GetProductByIdUseCase(
        ProductoRepository productoRepository, 
        ProductoMapper productoMapper,
        ProductValidationService productValidationService) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.productValidationService = productValidationService;
    }

    public ProductResponse execute(UUID id) {

        productValidationService.isUuidValidate(id.toString());

        return productoRepository.findById(id.toString())
                .map(productoMapper::toResponse)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
