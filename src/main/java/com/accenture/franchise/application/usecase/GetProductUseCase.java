package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.ProductResponse;
import com.accenture.franchise.application.mapper.ProductoMapper;
import com.accenture.franchise.domain.repository.ProductoRepository;

import java.util.List;

public class GetProductUseCase {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public GetProductUseCase(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    public List<ProductResponse> execute() {
        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toResponse)
                .toList();
    }
}
