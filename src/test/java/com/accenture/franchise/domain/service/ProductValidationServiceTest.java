package com.accenture.franchise.domain.service;

import com.accenture.franchise.domain.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductValidationServiceTest {

    private ProductValidationService service;

    @BeforeEach
    void setUp() {
        service = new ProductValidationService();
    }

    @Test
    void validate_validProduct_doesNotThrow() {
        Producto product = producto(UUID.randomUUID().toString(), "Producto A");
        assertDoesNotThrow(() -> service.validate(product));
    }

    @Test
    void validate_nullName_throwsIllegalArgumentException() {
        Producto product = producto(UUID.randomUUID().toString(), null);
        assertThrows(IllegalArgumentException.class, () -> service.validate(product));
    }

    @Test
    void validate_invalidUuidId_throwsIllegalArgumentException() {
        Producto product = producto("not-a-uuid", "Producto B");
        assertThrows(IllegalArgumentException.class, () -> service.validate(product));
    }

    @Test
    void validate_nullId_throwsIllegalArgumentException() {
        Producto product = producto(null, "Producto C");
        assertThrows(IllegalArgumentException.class, () -> service.validate(product));
    }

    @Test
    void isUuidValidate_validUuid_doesNotThrow() {
        String id = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> service.isUuidValidate(id));
    }

    @Test
    void isUuidValidate_invalidUuid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.isUuidValidate("bad-id"));
    }

    private Producto producto(String id, String name) {
        LocalDateTime now = LocalDateTime.now();
        return new Producto(id, name, true, now, now);
    }
}
