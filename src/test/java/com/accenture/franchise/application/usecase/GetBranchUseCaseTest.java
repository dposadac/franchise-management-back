package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.BranchResponse;
import com.accenture.franchise.application.mapper.SucursalMapper;
import com.accenture.franchise.domain.model.Sucursal;
import com.accenture.franchise.domain.repository.SucursalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetBranchUseCaseTest {

    @Mock private SucursalRepository sucursalRepository;
    @Mock private SucursalMapper sucursalMapper;

    private GetBranchUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetBranchUseCase(sucursalRepository, sucursalMapper);
    }

    @Test
    void execute_returnsAllBranches() {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        Sucursal sucursal = new Sucursal(id, "Sucursal Norte", now, now);
        BranchResponse response = new BranchResponse(id, "Sucursal Norte", now, now);

        when(sucursalRepository.findAll()).thenReturn(List.of(sucursal));
        when(sucursalMapper.toResponse(sucursal)).thenReturn(response);

        List<BranchResponse> result = useCase.execute();

        assertThat(result).containsExactly(response);
        verify(sucursalRepository).findAll();
    }

    @Test
    void execute_returnsEmptyList_whenNoBranches() {
        when(sucursalRepository.findAll()).thenReturn(List.of());

        List<BranchResponse> result = useCase.execute();

        assertThat(result).isEmpty();
        verify(sucursalMapper, never()).toResponse(any());
    }
}
