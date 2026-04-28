package com.accenture.franchise.application.dto;

import jakarta.validation.constraints.NotBlank;

public record GetAffiliationRequest(
        @NotBlank String franchiseId,
        @NotBlank String branchId
) {}
