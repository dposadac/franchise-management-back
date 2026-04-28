package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseRequest;
import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.mapper.FranchiseMapper;
import com.accenture.franchise.domain.model.Franquicia;
import com.accenture.franchise.domain.repository.FranquiciaRepository;
import com.accenture.franchise.domain.service.FranchiseValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateFranchiseUseCaseTest {

    @Mock private FranquiciaRepository franchiseRepository;
    @Mock private FranchiseValidationService validationService;
    @Mock private FranchiseMapper franchiseMapper;

    private CreateFranchiseUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateFranchiseUseCase(franchiseRepository, validationService, franchiseMapper);
    }

    @Test
    void execute_createsAndReturnsFranchise() {
        LocalDateTime now = LocalDateTime.now();
        FranchiseRequest request = new FranchiseRequest("Test Franchise");
        Franquicia domain = new Franquicia("id-1", "Test Franchise", now, now);
        FranchiseResponse response = new FranchiseResponse("id-1", "Test Franchise", now, now);

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
        LocalDateTime now = LocalDateTime.now();
        FranchiseRequest request = new FranchiseRequest("Bad Name");
        Franquicia domain = new Franquicia("id-2", null, now, now);

        when(franchiseMapper.toDomain(request)).thenReturn(domain);
        doThrow(new IllegalArgumentException("Franchise name must not be blank"))
                .when(validationService).validate(domain);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(request));
        verify(franchiseRepository, never()).save(any());
    }
}
