package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.BranchRequest;
import com.accenture.franchise.application.dto.BranchResponse;
import com.accenture.franchise.application.mapper.SucursalMapper;
import com.accenture.franchise.domain.model.Sucursal;
import com.accenture.franchise.domain.repository.SucursalRepository;
import com.accenture.franchise.domain.service.BranchValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBranchUseCaseTest {

    @Mock private SucursalRepository sucursalRepository;
    @Mock private SucursalMapper sucursalMapper;
    @Mock private BranchValidationService branchValidationService;

    private CreateBranchUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateBranchUseCase(sucursalRepository, sucursalMapper, branchValidationService);
    }

    @Test
    void execute_createsAndReturnsBranch() {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        BranchRequest request = new BranchRequest("Sucursal Norte");
        Sucursal domain = new Sucursal(id, "Sucursal Norte", now, now);
        BranchResponse response = new BranchResponse(id, "Sucursal Norte", now, now);

        when(sucursalMapper.toDomain(request)).thenReturn(domain);
        when(sucursalRepository.save(domain)).thenReturn(domain);
        when(sucursalMapper.toResponse(domain)).thenReturn(response);

        BranchResponse result = useCase.execute(request);

        assertThat(result).isEqualTo(response);
        verify(sucursalRepository).save(domain);
    }

    @Test
    void execute_savesAndMapsCorrectly() {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        BranchRequest request = new BranchRequest("Sucursal Sur");
        Sucursal domain = new Sucursal(id, "Sucursal Sur", now, now);
        BranchResponse response = new BranchResponse(id, "Sucursal Sur", now, now);

        when(sucursalMapper.toDomain(request)).thenReturn(domain);
        when(sucursalRepository.save(domain)).thenReturn(domain);
        when(sucursalMapper.toResponse(domain)).thenReturn(response);

        useCase.execute(request);

        verify(sucursalMapper).toDomain(request);
        verify(sucursalMapper).toResponse(domain);
    }
}
