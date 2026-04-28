package com.accenture.franchise.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InventoryRequest(
    @NotBlank String productId,
    @NotBlank String branchId,
    @NotBlank String statusId,
    @NotNull Integer stockQuantity
) {}
