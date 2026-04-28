package com.accenture.franchise.application.mapper;

import java.util.UUID;

import com.accenture.franchise.application.dto.ProductRequest;
import com.accenture.franchise.application.dto.ProductResponse;
import com.accenture.franchise.domain.model.Producto;
import com.accenture.franchise.infrastructure.shared.DateUtils;

public class ProductoMapper {
    public Producto toDomain(ProductRequest request){
        return new Producto(
            UUID.randomUUID().toString(),
            request.name(),
            request.isActive(),
            DateUtils.nowBogota(),
            DateUtils.nowBogota()
        );
    }

    public ProductResponse toResponse(Producto product){
        return new ProductResponse(
            product.getIdProducto(),
            product.getNombre(),
            product.isActivo(),
            product.getFechaCreacion(),
            product.getFechaActualizacion());
    }
}
