package com.accenture.franchise.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FranchiseRequest(
        @NotBlank String name,
        @NotBlank String address,
        String phone,
        @Email @NotBlank String email
) {}
