package com.accenture.franchise.domain.service;

import com.accenture.franchise.domain.model.Sucursal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BranchValidationServiceTest {

    private BranchValidationService service;

    @BeforeEach
    void setUp() {
        service = new BranchValidationService();
    }

    @Test
    void validate_validBranch_doesNotThrow() {
        Sucursal branch = sucursal(UUID.randomUUID().toString(), "Sucursal Norte");
        assertDoesNotThrow(() -> service.validate(branch));
    }

    @Test
    void validate_nullName_throwsIllegalArgumentException() {
        Sucursal branch = sucursal(UUID.randomUUID().toString(), null);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.validate(branch));
        assertEquals("Franchise name must not be blank", ex.getMessage());
    }

    @Test
    void validate_invalidUuidId_throwsIllegalArgumentException() {
        Sucursal branch = sucursal("not-a-valid-uuid", "Sucursal Sur");
        assertThrows(IllegalArgumentException.class, () -> service.validate(branch));
    }

    @Test
    void validate_nullId_throwsIllegalArgumentException() {
        Sucursal branch = sucursal(null, "Sucursal Este");
        assertThrows(IllegalArgumentException.class, () -> service.validate(branch));
    }

    @Test
    void isUuidValidate_validUuid_doesNotThrow() {
        String id = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> service.isUuidValidate(id));
    }

    @Test
    void isUuidValidate_invalidUuid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.isUuidValidate("invalid-id"));
    }

    private Sucursal sucursal(String id, String name) {
        LocalDateTime now = LocalDateTime.now();
        return new Sucursal(id, name, now, now);
    }
}
