package com.accenture.franchise.domain.service;

import com.accenture.franchise.domain.model.Franchise;

public class FranchiseValidationService {

    public void validate(Franchise franchise) {
        if (franchise.getName() == null || franchise.getName().isBlank()) {
            throw new IllegalArgumentException("Franchise name must not be blank");
        }
        if (franchise.getEmail() == null || !franchise.getEmail().contains("@")) {
            throw new IllegalArgumentException("Franchise email is invalid");
        }
    }
}
