package com.accenture.franchise.application.mapper;

import java.util.UUID;

import com.accenture.franchise.application.dto.BranchRequest;
import com.accenture.franchise.application.dto.BranchResponse;
import com.accenture.franchise.domain.model.Sucursal;
import com.accenture.franchise.infrastructure.shared.DateUtils;

public class SucursalMapper {
    
    public Sucursal toDomain(BranchRequest request){
        return new Sucursal(
            UUID.randomUUID().toString(),
            request.name(),
            DateUtils.nowBogota(),
            DateUtils.nowBogota()
        );
    }

    public BranchResponse toResponse(Sucursal branch){
        return new BranchResponse(
            branch.getIdSucursal(),
            branch.getNombre(),
            branch.getFechaCreacion(),
            branch.getFechaActualizacion()
        );
    }
}
