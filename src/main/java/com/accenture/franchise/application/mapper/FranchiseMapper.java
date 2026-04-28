package com.accenture.franchise.application.mapper;

import com.accenture.franchise.application.dto.FranchiseRequest;
import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.domain.model.Franquicia;
import com.accenture.franchise.infrastructure.shared.DateUtils;

import java.util.UUID;

public class FranchiseMapper {

    public Franquicia toDomain(FranchiseRequest request) {
        return new Franquicia(
                UUID.randomUUID().toString(),
                request.name(),
                DateUtils.nowBogota(),
                DateUtils.nowBogota()
        );
    }

    public FranchiseResponse toResponse(Franquicia franchise) {
        return new FranchiseResponse(
                franchise.getIdFranquicia(),
                franchise.getNombre(),
                franchise.getFechaCreacion(),
                franchise.getFechaActualizacion()
        );
    }
}
