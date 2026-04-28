package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.ProductResponse;
import com.accenture.franchise.application.mapper.ProductoMapper;
import com.accenture.franchise.domain.exception.ProductNotFoundException;
import com.accenture.franchise.domain.model.Producto;
import com.accenture.franchise.domain.repository.ProductoRepository;
import com.accenture.franchise.domain.service.ProductValidationService;
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
class GetProductByIdUseCaseTest {

    @Mock private ProductoRepository productoRepository;
    @Mock private ProductoMapper productoMapper;
    @Mock private ProductValidationService productValidationService;

    private GetProductByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetProductByIdUseCase(productoRepository, productoMapper, productValidationService);
    }

    @Test
    void execute_returnsProduct_whenExists() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Producto producto = new Producto(id.toString(), "Producto A", true, now, now);
        ProductResponse response = new ProductResponse(id.toString(), "Producto A", true, now, now);

        when(productoRepository.findById(id.toString())).thenReturn(Optional.of(producto));
        when(productoMapper.toResponse(producto)).thenReturn(response);

        ProductResponse result = useCase.execute(id);

        assertThat(result).isEqualTo(response);
        verify(productoRepository).findById(id.toString());
    }

    @Test
    void execute_throwsProductNotFoundException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(productoRepository.findById(id.toString())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> useCase.execute(id));
        verify(productoMapper, never()).toResponse(any());
    }
}
