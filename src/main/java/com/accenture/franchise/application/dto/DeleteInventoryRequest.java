package com.accenture.franchise.application.dto;

import jakarta.validation.constraints.NotBlank;

public record DeleteInventoryRequest(
        @NotBlank String productId,
        @NotBlank String branchId
) {}
