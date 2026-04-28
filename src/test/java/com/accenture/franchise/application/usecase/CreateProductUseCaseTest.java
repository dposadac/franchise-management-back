package com.accenture.franchise.application.usecase;

import com.accenture.franchise.application.dto.ProductRequest;
import com.accenture.franchise.application.dto.ProductResponse;
import com.accenture.franchise.application.mapper.ProductoMapper;
import com.accenture.franchise.domain.model.Producto;
import com.accenture.franchise.domain.repository.ProductoRepository;
import com.accenture.franchise.domain.service.ProductValidationService;
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
class CreateProductUseCaseTest {

    @Mock private ProductoRepository productoRepository;
    @Mock private ProductoMapper productoMapper;
    @Mock private ProductValidationService productValidationService;

    private CreateProductUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateProductUseCase(productoRepository, productoMapper, productValidationService);
    }

    @Test
    void execute_createsAndReturnsProduct() {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        ProductRequest request = new ProductRequest("Producto A", true);
        Producto domain = new Producto(id, "Producto A", true, now, now);
        ProductResponse response = new ProductResponse(id, "Producto A", true, now, now);

        when(productoMapper.toDomain(request)).thenReturn(domain);
        when(productoRepository.save(domain)).thenReturn(domain);
        when(productoMapper.toResponse(domain)).thenReturn(response);

        ProductResponse result = useCase.execute(request);

        assertThat(result).isEqualTo(response);
        verify(productValidationService).validate(domain);
        verify(productoRepository).save(domain);
    }

    @Test
    void execute_savesProductWithMappedData() {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        ProductRequest request = new ProductRequest("Producto B", false);
        Producto domain = new Producto(id, "Producto B", false, now, now);
        ProductResponse response = new ProductResponse(id, "Producto B", false, now, now);

        when(productoMapper.toDomain(request)).thenReturn(domain);
        when(productoRepository.save(domain)).thenReturn(domain);
        when(productoMapper.toResponse(domain)).thenReturn(response);

        ProductResponse result = useCase.execute(request);

        assertThat(result.isActive()).isFalse();
        verify(productoMapper).toDomain(request);
        verify(productoMapper).toResponse(domain);
    }
}
