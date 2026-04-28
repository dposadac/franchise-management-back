package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.ProductResponse;
import com.accenture.franchise.application.dto.UpdateProductRequest;
import com.accenture.franchise.application.mapper.ProductoMapper;
import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.model.Producto;
import com.accenture.franchise.domain.repository.ProductoRepository;
import com.accenture.franchise.domain.service.ProductValidationService;
import com.accenture.franchise.infrastructure.shared.DateUtils;


public class UpdateProductUseCase {
    
    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final ProductValidationService productValidationService;

    public UpdateProductUseCase(ProductoRepository productoRepository,
            ProductoMapper productoMapper,
            ProductValidationService productValidationService) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.productValidationService = productValidationService;
    }

    public ProductResponse execute(String id, UpdateProductRequest request) {
        productValidationService.isUuidValidate(id);

        Producto existing = productoRepository.findById(id)
                .orElseThrow(() -> new FranchiseNotFoundException(id));

        Producto updated = new Producto(
                existing.getIdProducto(),
                request.name(),
                request.isActive(),
                existing.getFechaCreacion(),
                DateUtils.nowBogota()
        );

        productValidationService.validate(updated);
        
        Producto saved = productoRepository.save(updated);
        return productoMapper.toResponse(saved);
    }
}
