package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.ProductRequest;
import com.accenture.franchise.application.dto.ProductResponse;
import com.accenture.franchise.application.mapper.ProductoMapper;
import com.accenture.franchise.domain.model.Producto;
import com.accenture.franchise.domain.repository.ProductoRepository;
import com.accenture.franchise.domain.service.ProductValidationService;

public class CreateProductUseCase {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final ProductValidationService productValidationService;

    public CreateProductUseCase(
        ProductoRepository productoRepository,
        ProductoMapper productoMapper,
        ProductValidationService productValidationService) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.productValidationService = productValidationService;
    }

    public ProductResponse execute(ProductRequest request) {
        Producto producto = productoMapper.toDomain(request);

        productValidationService.validate(producto);
        
        Producto saved = productoRepository.save(producto);
        return productoMapper.toResponse(saved);
    }
}
