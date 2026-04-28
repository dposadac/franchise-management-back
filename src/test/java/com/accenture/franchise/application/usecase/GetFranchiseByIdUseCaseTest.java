package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.FranchiseResponse;
import com.accenture.franchise.application.mapper.FranchiseMapper;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFranchiseByIdUseCaseTest {

    @Mock private FranquiciaRepository franchiseRepository;
    @Mock private FranchiseMapper franchiseMapper;
    @Mock private FranchiseValidationService franchiseValidationService;

    private GetFranchiseByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetFranchiseByIdUseCase(franchiseRepository, franchiseMapper, franchiseValidationService);
    }

    @Test
    void execute_existingId_returnsFranchise() {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        Franquicia franchise = new Franquicia(id, "Test Franchise", now, now);
        FranchiseResponse response = new FranchiseResponse(id, "Test Franchise", now, now);

        when(franchiseRepository.findById(id)).thenReturn(Optional.of(franchise));
        when(franchiseMapper.toResponse(franchise)).thenReturn(response);

        FranchiseResponse result = useCase.execute(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    void execute_nonExistingId_throwsFranchiseNotFoundException() {
        String id = UUID.randomUUID().toString();
        when(franchiseRepository.findById(id)).thenReturn(Optional.empty());

        FranchiseNotFoundException ex = assertThrows(FranchiseNotFoundException.class, () -> useCase.execute(id));
        assertThat(ex.getMessage()).contains(id);
    }
}
