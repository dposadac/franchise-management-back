package com.accenture.franchise.application.usecase;

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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteFranchiseUseCaseTest {

    @Mock private FranchiseRepository franchiseRepository;

    private DeleteFranchiseUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteFranchiseUseCase(franchiseRepository);
    }

    @Test
    void execute_existingId_deletesSuccessfully() {
        UUID id = UUID.randomUUID();
        Franchise franchise = new Franchise(id, "Name", "Addr", "123", "a@b.com", FranchiseStatus.ACTIVE);
        when(franchiseRepository.findById(id)).thenReturn(Optional.of(franchise));

        assertDoesNotThrow(() -> useCase.execute(id));
        verify(franchiseRepository).deleteById(id);
    }

    @Test
    void execute_nonExistingId_throwsFranchiseNotFoundException() {
        UUID id = UUID.randomUUID();
        when(franchiseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(FranchiseNotFoundException.class, () -> useCase.execute(id));
        verify(franchiseRepository, never()).deleteById(any());
    }
}
