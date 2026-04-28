package com.accenture.franchise.application.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateFranchiseRequest(
        @NotBlank String name
) {}
