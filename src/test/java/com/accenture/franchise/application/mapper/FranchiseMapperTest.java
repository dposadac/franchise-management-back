package com.accenture.franchise.application.mapper;

import com.accenture.franchise.application.dto.FranchiseRequest;
import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.domain.model.Franchise;
import com.accenture.franchise.domain.model.FranchiseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FranchiseMapperTest {

    private FranchiseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new FranchiseMapper();
    }

    @Test
    void toDomain_mapsAllFields_andGeneratesId() {
        FranchiseRequest request = new FranchiseRequest("Name", "Address", "555-1234", "email@test.com");

        Franchise domain = mapper.toDomain(request);

        assertThat(domain.getId()).isNotNull();
        assertThat(domain.getName()).isEqualTo("Name");
        assertThat(domain.getAddress()).isEqualTo("Address");
        assertThat(domain.getPhone()).isEqualTo("555-1234");
        assertThat(domain.getEmail()).isEqualTo("email@test.com");
        assertThat(domain.getStatus()).isEqualTo(FranchiseStatus.ACTIVE);
    }

    @Test
    void toDomain_generatesDifferentIdEachTime() {
        FranchiseRequest request = new FranchiseRequest("Name", "Addr", "123", "a@b.com");

        Franchise domain1 = mapper.toDomain(request);
        Franchise domain2 = mapper.toDomain(request);

        assertThat(domain1.getId()).isNotEqualTo(domain2.getId());
    }

    @Test
    void toResponse_mapsAllFields() {
        UUID id = UUID.randomUUID();
        Franchise franchise = new Franchise(id, "Name", "Addr", "123", "email@test.com", FranchiseStatus.INACTIVE);

        FranchiseResponse response = mapper.toResponse(franchise);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.name()).isEqualTo("Name");
        assertThat(response.address()).isEqualTo("Addr");
        assertThat(response.phone()).isEqualTo("123");
        assertThat(response.email()).isEqualTo("email@test.com");
        assertThat(response.status()).isEqualTo(FranchiseStatus.INACTIVE);
    }
}
