package com.accenture.franchise.application.usecase;

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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteProductUseCaseTest {

    @Mock private ProductoRepository productoRepository;
    @Mock private ProductValidationService productValidationService;

    private DeleteProductUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeleteProductUseCase(productoRepository, productValidationService);
    }

    @Test
    void execute_deletesProduct_whenExists() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Producto producto = new Producto(id.toString(), "Producto A", true, now, now);

        when(productoRepository.findById(id.toString())).thenReturn(Optional.of(producto));

        useCase.execute(id);

        verify(productoRepository).deleteById(id.toString());
    }

    @Test
    void execute_throwsProductNotFoundException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(productoRepository.findById(id.toString())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> useCase.execute(id));
        verify(productoRepository, never()).deleteById(any());
    }
}
