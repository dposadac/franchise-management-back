package com.accenture.franchise.domain.exception;

import java.util.UUID;

public class BranchNotFoundException extends RuntimeException {

    public BranchNotFoundException(UUID id) {
        super("Branch not found with id: " + id);
    }

    public BranchNotFoundException(String id) {
        super("Branch not found with id: " + id);
    }
}
