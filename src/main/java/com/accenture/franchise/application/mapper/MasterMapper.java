package com.accenture.franchise.application.mapper;

import com.accenture.franchise.application.dto.Response;

import java.util.List;

public class MasterMapper {

    public Response toResponse(String message, boolean isSucessResponse, Object info) {
        return new Response(
                message,
                isSucessResponse,
                info
        );
    }

    public Response toResponse(String message, boolean isSucessResponse, Object info, List<Object> items) {
        return new Response(
                message,
                isSucessResponse,
                info,
                items
        );
    }
}