package com.accenture.franchise.application.usecase;

import com.accenture.franchise.domain.exception.BranchNotFoundException;
import com.accenture.franchise.domain.model.Sucursal;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.domain.service.BranchValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteBranchUseCaseTest {

    @Mock private SucursalRepository sucursalRepository;
    @Mock private BranchValidationService branchValidationService;

    private DeleteBranchUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteBranchUseCase(sucursalRepository, branchValidationService);
    }

    @Test
    void execute_deletesBranch_whenExists() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Sucursal sucursal = new Sucursal(id.toString(), "Sucursal Norte", now, now);

        when(sucursalRepository.findById(id.toString())).thenReturn(Optional.of(sucursal));

        useCase.execute(id);

        verify(sucursalRepository).deleteById(id.toString());
    }

    @Test
    void execute_throwsBranchNotFoundException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(sucursalRepository.findById(id.toString())).thenReturn(Optional.empty());

        assertThrows(BranchNotFoundException.class, () -> useCase.execute(id));
        verify(sucursalRepository, never()).deleteById(any());
    }
}
