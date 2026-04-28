package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.dto.UpdateFranchiseRequest;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.model.Franquicia;
import com.accenture.franchise.domain.repository.FranquiciaRepository;
import com.accenture.franchise.domain.service.FranchiseValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateFranchiseUseCaseTest {

    @Mock private FranquiciaRepository franchiseRepository;
    @Mock private FranchiseValidationService validationService;
    @Mock private FranchiseMapper franchiseMapper;

    private UpdateFranchiseUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateFranchiseUseCase(franchiseRepository, validationService, franchiseMapper);
    }

    @Test
    void execute_existingFranchise_updatesAndReturns() {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        Franquicia existing = new Franquicia(id, "Old Name", now, now);
        UpdateFranchiseRequest request = new UpdateFranchiseRequest("New Name");
        FranchiseResponse response = new FranchiseResponse(id, "New Name", now, now);

        when(franchiseRepository.findById(id)).thenReturn(Optional.of(existing));
        when(franchiseRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(franchiseMapper.toResponse(any())).thenReturn(response);

        FranchiseResponse result = useCase.execute(id, request);

        assertThat(result).isEqualTo(response);

        ArgumentCaptor<Franquicia> captor = ArgumentCaptor.forClass(Franquicia.class);
        verify(franchiseRepository).save(captor.capture());
        assertThat(captor.getValue().getNombre()).isEqualTo("New Name");
        assertThat(captor.getValue().getIdFranquicia()).isEqualTo(id);
    }

    @Test
    void execute_nonExistingId_throwsFranchiseNotFoundException() {
        String id = UUID.randomUUID().toString();
        UpdateFranchiseRequest request = new UpdateFranchiseRequest("Name");
        when(franchiseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(FranchiseNotFoundException.class, () -> useCase.execute(id, request));
        verify(franchiseRepository, never()).save(any());
    }
}
