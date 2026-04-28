package com.accenture.franchise.application.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateProductRequest(
    @NotBlank String name,
    boolean isActive
){ } 