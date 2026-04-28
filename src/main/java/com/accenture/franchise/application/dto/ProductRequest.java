package com.accenture.franchise.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductRequest(
        @NotBlank String name,
        boolean isActive
) {}

