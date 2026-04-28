package com.accenture.franchise.domain.service;

import com.accenture.franchise.domain.model.Franquicia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FranchiseValidationServiceTest {

    private FranchiseValidationService service;

    @BeforeEach
    void setUp() {
        service = new FranchiseValidationService();
    }

    @Test
    void validate_validFranchise_doesNotThrow() {
        Franquicia franchise = franquicia(UUID.randomUUID().toString(), "Test Franchise");
        assertDoesNotThrow(() -> service.validate(franchise));
    }

    @Test
    void validate_nullName_throwsIllegalArgumentException() {
        Franquicia franchise = franquicia(UUID.randomUUID().toString(), null);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.validate(franchise));
        assertEquals("Franchise name must not be blank", ex.getMessage());
    }

    @Test
    void validate_invalidUuidId_throwsIllegalArgumentException() {
        Franquicia franchise = franquicia("not-a-valid-uuid", "Test");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.validate(franchise));
        assertEquals("Franchise email is invalid", ex.getMessage());
    }

    @Test
    void validate_nullId_throwsIllegalArgumentException() {
        Franquicia franchise = franquicia(null, "Test");
        assertThrows(IllegalArgumentException.class, () -> service.validate(franchise));
    }

    @Test
    void isUuidValidate_validUuid_doesNotThrow() {
        String id = UUID.randomUUID().toString();
        assertDoesNotThrow(() -> service.isUuidValidate(id));
    }

    @Test
    void isUuidValidate_invalidUuid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.isUuidValidate("not-a-uuid"));
    }

    private Franquicia franquicia(String id, String name) {
        LocalDateTime now = LocalDateTime.now();
        return new Franquicia(id, name, now, now);
    }
}
