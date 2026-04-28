package com.accenture.franchise.infrastructure.shared;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

    private static final ZoneId BOGOTA_ZONE = ZoneId.of("America/Bogota");

    private DateUtils() {
    }

    public static LocalDateTime nowBogota() {
        return LocalDateTime.now(BOGOTA_ZONE);
    }
}
