package com.accenture.franchise.application.usecase;

import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.model.Franquicia;
import com.accenture.franchise.domain.repository.FranquiciaRepository;
import com.accenture.franchise.domain.service.FranchiseValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteFranchiseUseCaseTest {

    @Mock private FranquiciaRepository franchiseRepository;
    @Mock private FranchiseValidationService franchiseValidationService;

    private DeleteFranchiseUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteFranchiseUseCase(franchiseRepository, franchiseValidationService);
    }

    @Test
    void execute_existingId_deletesSuccessfully() {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        Franquicia franchise = new Franquicia(id, "Test", now, now);
        when(franchiseRepository.findById(id)).thenReturn(Optional.of(franchise));

        assertDoesNotThrow(() -> useCase.execute(id));
        verify(franchiseRepository).deleteById(id);
    }

    @Test
    void execute_nonExistingId_throwsFranchiseNotFoundException() {
        String id = UUID.randomUUID().toString();
        when(franchiseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(FranchiseNotFoundException.class, () -> useCase.execute(id));
        verify(franchiseRepository, never()).deleteById(any());
    }
}
