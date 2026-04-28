package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.BranchResponse;
import com.accenture.franchise.application.mapper.SucursalMapper;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetBranchByIdUseCaseTest {

    @Mock private SucursalRepository sucursalRepository;
    @Mock private SucursalMapper sucursalMapper;
    @Mock private BranchValidationService branchValidationService;

    private GetBranchByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetBranchByIdUseCase(sucursalRepository, sucursalMapper, branchValidationService);
    }

    @Test
    void execute_returnsBranch_whenExists() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Sucursal sucursal = new Sucursal(id.toString(), "Sucursal Norte", now, now);
        BranchResponse response = new BranchResponse(id.toString(), "Sucursal Norte", now, now);

        when(sucursalRepository.findById(id.toString())).thenReturn(Optional.of(sucursal));
        when(sucursalMapper.toResponse(sucursal)).thenReturn(response);

        BranchResponse result = useCase.execute(id);

        assertThat(result).isEqualTo(response);
        verify(sucursalRepository).findById(id.toString());
    }

    @Test
    void execute_throwsBranchNotFoundException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(sucursalRepository.findById(id.toString())).thenReturn(Optional.empty());

        assertThrows(BranchNotFoundException.class, () -> useCase.execute(id));
        verify(sucursalMapper, never()).toResponse(any());
    }
}
