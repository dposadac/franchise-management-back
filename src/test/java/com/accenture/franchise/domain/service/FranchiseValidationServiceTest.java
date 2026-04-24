package com.accenture.franchise.domain.service;

import com.accenture.franchise.domain.model.Franchise;
import com.accenture.franchise.domain.model.FranchiseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Franchise franchise = franchise("Test", "test@example.com");
        assertDoesNotThrow(() -> service.validate(franchise));
    }

    @Test
    void validate_blankName_throwsIllegalArgumentException() {
        Franchise franchise = franchise("", "test@example.com");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.validate(franchise));
        assertEquals("Franchise name must not be blank", ex.getMessage());
    }

    @Test
    void validate_nullName_throwsIllegalArgumentException() {
        Franchise franchise = franchise(null, "test@example.com");
        assertThrows(IllegalArgumentException.class, () -> service.validate(franchise));
    }

    @Test
    void validate_emailWithoutAt_throwsIllegalArgumentException() {
        Franchise franchise = franchise("Test", "invalid-email");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.validate(franchise));
        assertEquals("Franchise email is invalid", ex.getMessage());
    }

    @Test
    void validate_nullEmail_throwsIllegalArgumentException() {
        Franchise franchise = franchise("Test", null);
        assertThrows(IllegalArgumentException.class, () -> service.validate(franchise));
    }

    private Franchise franchise(String name, String email) {
        return new Franchise(UUID.randomUUID(), name, "Addr", "123", email, FranchiseStatus.ACTIVE);
    }
}
