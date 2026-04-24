package com.accenture.franchise.domain.exception;

import java.util.UUID;

public class FranchiseNotFoundException extends RuntimeException {

    public FranchiseNotFoundException(UUID id) {
        super("Franchise not found with id: " + id);
    }
}
