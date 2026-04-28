package com.accenture.franchise.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateInventoryRequest(
    @NotBlank String productId,
    @NotBlank String branchId,
    @NotNull Integer stockQuantity
) {}