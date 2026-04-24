package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.model.Franchise;
import com.accenture.franchise.domain.model.FranchiseStatus;
import com.accenture.franchise.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFranchisesUseCaseTest {

    @Mock private FranchiseRepository franchiseRepository;
    @Mock private FranchiseMapper franchiseMapper;

    private GetFranchisesUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetFranchisesUseCase(franchiseRepository, franchiseMapper);
    }

    @Test
    void execute_returnsAllFranchises() {
        UUID id = UUID.randomUUID();
        Franchise franchise = new Franchise(id, "Name", "Addr", "123", "a@b.com", FranchiseStatus.ACTIVE);
        FranchiseResponse response = new FranchiseResponse(id, "Name", "Addr", "123", "a@b.com", FranchiseStatus.ACTIVE);

        when(franchiseRepository.findAll()).thenReturn(List.of(franchise));
        when(franchiseMapper.toResponse(franchise)).thenReturn(response);

        List<FranchiseResponse> result = useCase.execute();

        assertThat(result).hasSize(1).containsExactly(response);
    }

    @Test
    void execute_emptyRepository_returnsEmptyList() {
        when(franchiseRepository.findAll()).thenReturn(List.of());

        List<FranchiseResponse> result = useCase.execute();

        assertThat(result).isEmpty();
    }
}
