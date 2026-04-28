package com.accenture.franchise.application.usecase;

import com.accenture.franchise.domain.exception.ProductNotFoundException;
import com.accenture.franchise.domain.repository.ProductoRepository;
import com.accenture.franchise.domain.service.ProductValidationService;

import java.util.UUID;

public class DeleteProductUseCase {

    private final ProductoRepository productoRepository;
    private final ProductValidationService productValidationService;

    public DeleteProductUseCase(ProductoRepository productoRepository, ProductValidationService productValidationService) {
        this.productoRepository = productoRepository;
        this.productValidationService = productValidationService;
    }

    public void execute(UUID id) {

        productValidationService.isUuidValidate(id.toString());

        productoRepository.findById(id.toString())
                .orElseThrow(() -> new ProductNotFoundException(id));
                
        productoRepository.deleteById(id.toString());
    }
}
