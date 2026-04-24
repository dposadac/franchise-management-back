package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseRequest;
import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.model.Franchise;
import com.accenture.franchise.domain.model.FranchiseStatus;
import com.accenture.franchise.domain.repository.FranchiseRepository;
import com.accenture.franchise.domain.service.FranchiseValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateFranchiseUseCaseTest {

    @Mock private FranchiseRepository franchiseRepository;
    @Mock private FranchiseValidationService validationService;
    @Mock private FranchiseMapper franchiseMapper;

    private CreateFranchiseUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateFranchiseUseCase(franchiseRepository, validationService, franchiseMapper);
    }

    @Test
    void execute_createsAndReturnsFranchise() {
        UUID id = UUID.randomUUID();
        FranchiseRequest request = new FranchiseRequest("Name", "Addr", "123", "a@b.com");
        Franchise domain = new Franchise(id, "Name", "Addr", "123", "a@b.com", FranchiseStatus.ACTIVE);
        FranchiseResponse response = new FranchiseResponse(id, "Name", "Addr", "123", "a@b.com", FranchiseStatus.ACTIVE);

        when(franchiseMapper.toDomain(request)).thenReturn(domain);
        when(franchiseRepository.save(domain)).thenReturn(domain);
        when(franchiseMapper.toResponse(domain)).thenReturn(response);

        FranchiseResponse result = useCase.execute(request);

        assertThat(result).isEqualTo(response);
        verify(validationService).validate(domain);
        verify(franchiseRepository).save(domain);
    }

    @Test
    void execute_validationFails_propagatesException() {
        FranchiseRequest request = new FranchiseRequest("", "Addr", "123", "a@b.com");
        Franchise domain = new Franchise(UUID.randomUUID(), "", "Addr", "123", "a@b.com", FranchiseStatus.ACTIVE);

        when(franchiseMapper.toDomain(request)).thenReturn(domain);
        doThrow(new IllegalArgumentException("Franchise name must not be blank"))
                .when(validationService).validate(domain);

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> useCase.execute(request));
        verify(franchiseRepository, never()).save(any());
    }
}
