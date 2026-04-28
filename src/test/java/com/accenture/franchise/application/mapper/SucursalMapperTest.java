package com.accenture.franchise.application.mapper;

import com.accenture.franchise.application.dto.BranchRequest;
import com.accenture.franchise.application.dto.BranchResponse;
import com.accenture.franchise.domain.model.Sucursal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class SucursalMapperTest {

    private SucursalMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new SucursalMapper();
    }

    @Test
    void toDomain_mapsNameAndGeneratesId() {
        BranchRequest request = new BranchRequest("Sucursal Norte");

        Sucursal domain = mapper.toDomain(request);

        assertThat(domain.getIdSucursal()).isNotNull();
        assertThat(domain.getNombre()).isEqualTo("Sucursal Norte");
        assertThat(domain.getFechaCreacion()).isNotNull();
        assertThat(domain.getFechaActualizacion()).isNotNull();
    }

    @Test
    void toDomain_generatesDifferentIdEachTime() {
        BranchRequest request = new BranchRequest("Branch");

        Sucursal domain1 = mapper.toDomain(request);
        Sucursal domain2 = mapper.toDomain(request);

        assertThat(domain1.getIdSucursal()).isNotEqualTo(domain2.getIdSucursal());
    }

    @Test
    void toResponse_mapsAllFields() {
        LocalDateTime now = LocalDateTime.now();
        Sucursal branch = new Sucursal("id-456", "Sucursal Sur", now, now);

        BranchResponse response = mapper.toResponse(branch);

        assertThat(response.id()).isEqualTo("id-456");
        assertThat(response.name()).isEqualTo("Sucursal Sur");
        assertThat(response.creationDate()).isEqualTo(now);
        assertThat(response.lastUpdated()).isEqualTo(now);
    }
}
