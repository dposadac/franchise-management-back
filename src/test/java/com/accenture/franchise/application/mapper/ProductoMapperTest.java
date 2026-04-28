package com.accenture.franchise.application.mapper;

import com.accenture.franchise.application.dto.ProductRequest;
import com.accenture.franchise.application.dto.ProductResponse;
import com.accenture.franchise.domain.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ProductoMapperTest {

    private ProductoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductoMapper();
    }

    @Test
    void toDomain_mapsFieldsAndGeneratesId() {
        ProductRequest request = new ProductRequest("Producto X", true);

        Producto domain = mapper.toDomain(request);

        assertThat(domain.getIdProducto()).isNotNull();
        assertThat(domain.getNombre()).isEqualTo("Producto X");
        assertThat(domain.isActivo()).isTrue();
        assertThat(domain.getFechaCreacion()).isNotNull();
        assertThat(domain.getFechaActualizacion()).isNotNull();
    }

    @Test
    void toDomain_generatesDifferentIdEachTime() {
        ProductRequest request = new ProductRequest("Producto Y", false);

        Producto domain1 = mapper.toDomain(request);
        Producto domain2 = mapper.toDomain(request);

        assertThat(domain1.getIdProducto()).isNotEqualTo(domain2.getIdProducto());
    }

    @Test
    void toDomain_inactiveFlagMapped() {
        ProductRequest request = new ProductRequest("Producto Z", false);

        Producto domain = mapper.toDomain(request);

        assertThat(domain.isActivo()).isFalse();
    }

    @Test
    void toResponse_mapsAllFields() {
        LocalDateTime now = LocalDateTime.now();
        Producto product = new Producto("id-789", "Producto A", true, now, now);

        ProductResponse response = mapper.toResponse(product);

        assertThat(response.id()).isEqualTo("id-789");
        assertThat(response.name()).isEqualTo("Producto A");
        assertThat(response.isActive()).isTrue();
        assertThat(response.creationDate()).isEqualTo(now);
        assertThat(response.lastUpdated()).isEqualTo(now);
    }
}
