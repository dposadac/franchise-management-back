package com.accenture.franchise.application.dto;

import com.accenture.franchise.domain.model.FranchiseStatus;

import java.util.UUID;

public record FranchiseResponse(
        UUID id,
        String name,
        String address,
        String phone,
        String email,
        FranchiseStatus status
) {}
