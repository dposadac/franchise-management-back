package com.accenture.franchise.application.dto;

import jakarta.validation.constraints.NotBlank;

public record BranchRequest(
        @NotBlank String name
) {}
