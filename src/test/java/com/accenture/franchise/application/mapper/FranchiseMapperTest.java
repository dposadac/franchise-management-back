package com.accenture.franchise.application.mapper;

import com.accenture.franchise.application.dto.FranchiseRequest;
import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.domain.model.Franquicia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FranchiseMapperTest {

    private FranchiseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new FranchiseMapper();
    }

    @Test
    void toDomain_mapsNameAndGeneratesId() {
        FranchiseRequest request = new FranchiseRequest("TestFranchise");

        Franquicia domain = mapper.toDomain(request);

        assertThat(domain.getIdFranquicia()).isNotNull();
        assertThat(domain.getNombre()).isEqualTo("TestFranchise");
        assertThat(domain.getFechaCreacion()).isNotNull();
        assertThat(domain.getFechaActualizacion()).isNotNull();
    }

    @Test
    void toDomain_generatesDifferentIdEachTime() {
        FranchiseRequest request = new FranchiseRequest("Name");

        Franquicia domain1 = mapper.toDomain(request);
        Franquicia domain2 = mapper.toDomain(request);

        assertThat(domain1.getIdFranquicia()).isNotEqualTo(domain2.getIdFranquicia());
    }

    @Test
    void toResponse_mapsAllFields() {
        LocalDateTime now = LocalDateTime.now();
        Franquicia franchise = new Franquicia("id-123", "MyFranchise", now, now);

        FranchiseResponse response = mapper.toResponse(franchise);

        assertThat(response.id()).isEqualTo("id-123");
        assertThat(response.name()).isEqualTo("MyFranchise");
        assertThat(response.creationDate()).isEqualTo(now);
        assertThat(response.lastUpdated()).isEqualTo(now);
    }
}
