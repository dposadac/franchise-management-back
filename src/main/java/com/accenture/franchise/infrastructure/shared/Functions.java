package com.accenture.franchise.infrastructure.shared;

public final class Functions {
    
    private Functions() {}

    public static void isUuidValidate(String id) {
        if (!UuidValidator.isValid(id)) {
            throw new IllegalArgumentException("Invalid UUID: does not match the expected UUID format (e.g. 550e8400-e29b-41d4-a716-446655440000).");
        }
    }
}
