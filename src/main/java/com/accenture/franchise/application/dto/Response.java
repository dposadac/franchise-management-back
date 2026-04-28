package com.accenture.franchise.application.dto;

import java.util.Collections;
import java.util.List;

public record Response(
    String Message,
    boolean isSuccess,
    Object info,
    List<Object> items
) {
    public Response(String Message, boolean isSuccess, Object info) {
        this(Message, isSuccess, info, Collections.emptyList());
    }

    public Response {
        if (items == null) {
            items = Collections.emptyList();
        }
    }
}
