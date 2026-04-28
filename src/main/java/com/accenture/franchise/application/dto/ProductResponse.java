package com.accenture.franchise.application.dto;

import java.time.LocalDateTime;

public record ProductResponse(
        String id,
        String name,
        boolean isActive,
        LocalDateTime creationDate,
        LocalDateTime lastUpdated
) {}
