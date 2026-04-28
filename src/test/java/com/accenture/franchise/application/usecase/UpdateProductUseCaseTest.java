package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.ProductResponse;
import com.accenture.franchise.application.dto.UpdateProductRequest;
import com.accenture.franchise.application.mapper.ProductoMapper;
import com.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.accenture.franchise.domain.model.Producto;
import com.accenture.franchise.domain.repository.ProductoRepository;
import com.accenture.franchise.domain.service.ProductValidationService;
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
class UpdateProductUseCaseTest {

    @Mock private ProductoRepository productoRepository;
    @Mock private ProductoMapper productoMapper;
    @Mock private ProductValidationService productValidationService;

    private UpdateProductUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdateProductUseCase(productoRepository, productoMapper, productValidationService);
    }

    @Test
    void execute_existingProduct_updatesAndReturns() {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        Producto existing = new Producto(id, "Old Product", true, now, now);
        UpdateProductRequest request = new UpdateProductRequest("New Product", false);
        ProductResponse response = new ProductResponse(id, "New Product", false, now, now);

        when(productoRepository.findById(id)).thenReturn(Optional.of(existing));
        when(productoRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(productoMapper.toResponse(any())).thenReturn(response);

        ProductResponse result = useCase.execute(id, request);

        assertThat(result).isEqualTo(response);

        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        verify(productoRepository).save(captor.capture());
        assertThat(captor.getValue().getNombre()).isEqualTo("New Product");
        assertThat(captor.getValue().isActivo()).isFalse();
    }

    @Test
    void execute_nonExistingId_throwsFranchiseNotFoundException() {
        String id = UUID.randomUUID().toString();
        UpdateProductRequest request = new UpdateProductRequest("Name", true);
        when(productoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(FranchiseNotFoundException.class, () -> useCase.execute(id, request));
        verify(productoRepository, never()).save(any());
    }
}
