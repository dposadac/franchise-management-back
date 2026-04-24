package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.dto.UpdateFranchiseRequest;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.model.Franchise;
import com.accenture.franchise.domain.model.FranchiseStatus;
import com.accenture.franchise.domain.repository.FranchiseRepository;
import com.accenture.franchise.domain.service.FranchiseValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateFranchiseUseCaseTest {

    @Mock private FranchiseRepository franchiseRepository;
    @Mock private FranchiseValidationService validationService;
    @Mock private FranchiseMapper franchiseMapper;

    private UpdateFranchiseUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateFranchiseUseCase(franchiseRepository, validationService, franchiseMapper);
    }

    @Test
    void execute_existingFranchise_updatesAndReturns() {
        UUID id = UUID.randomUUID();
        Franchise existing = new Franchise(id, "Old Name", "Old Addr", "000", "old@b.com", FranchiseStatus.ACTIVE);
        UpdateFranchiseRequest request = new UpdateFranchiseRequest("New Name", "New Addr", "999", "new@b.com");
        FranchiseResponse response = new FranchiseResponse(id, "New Name", "New Addr", "999", "new@b.com", FranchiseStatus.ACTIVE);

        when(franchiseRepository.findById(id)).thenReturn(Optional.of(existing));
        when(franchiseRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(franchiseMapper.toResponse(any())).thenReturn(response);

        FranchiseResponse result = useCase.execute(id, request);

        assertThat(result).isEqualTo(response);

        ArgumentCaptor<Franchise> captor = ArgumentCaptor.forClass(Franchise.class);
        verify(franchiseRepository).save(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("New Name");
        assertThat(captor.getValue().getId()).isEqualTo(id);
        assertThat(captor.getValue().getStatus()).isEqualTo(FranchiseStatus.ACTIVE);
    }

    @Test
    void execute_nonExistingId_throwsFranchiseNotFoundException() {
        UUID id = UUID.randomUUID();
        UpdateFranchiseRequest request = new UpdateFranchiseRequest("Name", "Addr", "123", "a@b.com");
        when(franchiseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(FranchiseNotFoundException.class, () -> useCase.execute(id, request));
        verify(franchiseRepository, never()).save(any());
    }
}
