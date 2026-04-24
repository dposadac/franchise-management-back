package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.model.Franchise;
import com.accenture.franchise.domain.model.FranchiseStatus;
import com.accenture.franchise.domain.repository.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFranchiseByIdUseCaseTest {

    @Mock private FranchiseRepository franchiseRepository;
    @Mock private FranchiseMapper franchiseMapper;

    private GetFranchiseByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetFranchiseByIdUseCase(franchiseRepository, franchiseMapper);
    }

    @Test
    void execute_existingId_returnsFranchise() {
        UUID id = UUID.randomUUID();
        Franchise franchise = new Franchise(id, "Name", "Addr", "123", "a@b.com", FranchiseStatus.ACTIVE);
        FranchiseResponse response = new FranchiseResponse(id, "Name", "Addr", "123", "a@b.com", FranchiseStatus.ACTIVE);

        when(franchiseRepository.findById(id)).thenReturn(Optional.of(franchise));
        when(franchiseMapper.toResponse(franchise)).thenReturn(response);

        FranchiseResponse result = useCase.execute(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void execute_nonExistingId_throwsFranchiseNotFoundException() {
        UUID id = UUID.randomUUID();
        when(franchiseRepository.findById(id)).thenReturn(Optional.empty());

        FranchiseNotFoundException ex = assertThrows(FranchiseNotFoundException.class, () -> useCase.execute(id));
        assertThat(ex.getMessage()).contains(id.toString());
    }
}
