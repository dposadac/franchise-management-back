package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.BranchResponse;
import com.accenture.franchise.application.dto.UpdateBranchRequest;
import com.accenture.franchise.application.mapper.SucursalMapper;
import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.model.Sucursal;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.domain.service.BranchValidationService;
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
class UpdateBranchUseCaseTest {

    @Mock private SucursalRepository branchRepository;
    @Mock private SucursalMapper branchMapper;
    @Mock private BranchValidationService branchValidationService;

    private UpdateBranchUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateBranchUseCase(branchRepository, branchMapper, branchValidationService);
    }

    @Test
    void execute_existingBranch_updatesAndReturns() {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        Sucursal existing = new Sucursal(id, "Old Branch", now, now);
        UpdateBranchRequest request = new UpdateBranchRequest("New Branch");
        BranchResponse response = new BranchResponse(id, "New Branch", now, now);

        when(branchRepository.findById(id)).thenReturn(Optional.of(existing));
        when(branchRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(branchMapper.toResponse(any())).thenReturn(response);

        BranchResponse result = useCase.execute(id, request);

        assertThat(result).isEqualTo(response);

        ArgumentCaptor<Sucursal> captor = ArgumentCaptor.forClass(Sucursal.class);
        verify(branchRepository).save(captor.capture());
        assertThat(captor.getValue().getNombre()).isEqualTo("New Branch");
        assertThat(captor.getValue().getIdSucursal()).isEqualTo(id);
    }

    @Test
    void execute_nonExistingId_throwsFranchiseNotFoundException() {
        String id = UUID.randomUUID().toString();
        UpdateBranchRequest request = new UpdateBranchRequest("Name");
        when(branchRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(FranchiseNotFoundException.class, () -> useCase.execute(id, request));
        verify(branchRepository, never()).save(any());
    }
}
