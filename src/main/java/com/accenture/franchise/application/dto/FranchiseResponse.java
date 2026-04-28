package com.accenture.franchise.application.dto;

import java.time.LocalDateTime;

public record FranchiseResponse(
        String id,
        String name,
        LocalDateTime creationDate,
        LocalDateTime lastUpdated
) {}
