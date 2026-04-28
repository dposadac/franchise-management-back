package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.ProductResponse;
import com.accenture.franchise.application.mapper.ProductoMapper;
import com.accenture.franchise.domain.model.Producto;
import com.accenture.franchise.domain.repository.ProductoRepository;
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
class GetProductUseCaseTest {

    @Mock private ProductoRepository productoRepository;
    @Mock private ProductoMapper productoMapper;

    private GetProductUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GetProductUseCase(productoRepository, productoMapper);
    }

    @Test
    void execute_returnsAllProducts() {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        Producto producto = new Producto(id, "Producto A", true, now, now);
        ProductResponse response = new ProductResponse(id, "Producto A", true, now, now);

        when(productoRepository.findAll()).thenReturn(List.of(producto));
        when(productoMapper.toResponse(producto)).thenReturn(response);

        List<ProductResponse> result = useCase.execute();

        assertThat(result).containsExactly(response);
        verify(productoRepository).findAll();
    }

    @Test
    void execute_returnsEmptyList_whenNoProducts() {
        when(productoRepository.findAll()).thenReturn(List.of());

        List<ProductResponse> result = useCase.execute();

        assertThat(result).isEmpty();
        verify(productoMapper, never()).toResponse(any());
    }
}
