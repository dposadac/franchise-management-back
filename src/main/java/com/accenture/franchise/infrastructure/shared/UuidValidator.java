package com.accenture.franchise.infrastructure.shared;

import java.util.regex.Pattern;

public final class UuidValidator {

    private static final Pattern UUID_REGEX = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}"
            + "-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$"
    );

    private UuidValidator() {}

    public static boolean isValid(String value) {
        if (value == null) return false;
        if (value.isBlank()) return false;
        return UUID_REGEX.matcher(value).matches();
    }
}