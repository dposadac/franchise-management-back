package com.accenture.franchise.domain.service;

import com.accenture.franchise.domain.model.Sucursal;
import com.accenture.franchise.infrastructure.shared.UuidValidator;

public class BranchValidationService {
    public void validate(Sucursal sucursal) {
        if (sucursal.getNombre() == null) {
            throw new IllegalArgumentException("Franchise name must not be blank");
        }
        if (!UuidValidator.isValid(sucursal.getIdSucursal())) {
            throw new IllegalArgumentException("Invalid UUID: does not match the expected UUID format (e.g. 550e8400-e29b-41d4-a716-446655440000).");
        }
    }

    public void isUuidValidate(String id) {
        if (!UuidValidator.isValid(id)) {
            throw new IllegalArgumentException("Invalid UUID: does not match the expected UUID format (e.g. 550e8400-e29b-41d4-a716-446655440000).");
        }
    }
}
