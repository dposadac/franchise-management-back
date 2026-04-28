package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaProductoRepositoryTest {

    @Mock private JpaProductoRepositorySpring springRepository;

    private JpaProductoRepository repository;

    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        repository = new JpaProductoRepository(springRepository);
    }

    @Test
    void save_persistsAndReturnsMappedDomain() {
        Producto domain = new Producto(UUID.randomUUID().toString(), "Hamburguesa", true, now, now);
        ProductoEntity entity = new ProductoEntity("P1", "Hamburguesa", true, now, now);
        when(springRepository.save(any(ProductoEntity.class))).thenReturn(entity);

        Producto result = repository.save(domain);

        assertThat(result.getIdProducto()).isEqualTo("P1");
        assertThat(result.getNombre()).isEqualTo("Hamburguesa");
        assertThat(result.isActivo()).isTrue();
        verify(springRepository).save(any(ProductoEntity.class));
    }

    @Test
    void findById_existing_returnsMappedDomain() {
        when(springRepository.findById("P1"))
                .thenReturn(Optional.of(new ProductoEntity("P1", "Hamburguesa", true, now, now)));

        Optional<Producto> result = repository.findById("P1");

        assertThat(result).isPresent();
        assertThat(result.get().getIdProducto()).isEqualTo("P1");
    }

    @Test
    void findById_notFound_returnsEmpty() {
        when(springRepository.findById("NOPE")).thenReturn(Optional.empty());

        assertThat(repository.findById("NOPE")).isEmpty();
    }

    @Test
    void findAll_returnsMappedList() {
        when(springRepository.findAll()).thenReturn(List.of(
                new ProductoEntity("P1", "Hamburguesa", true, now, now),
                new ProductoEntity("P2", "Pizza", false, now, now)
        ));

        List<Producto> result = repository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(1).getNombre()).isEqualTo("Pizza");
    }

    @Test
    void deleteById_delegatesToSpring() {
        repository.deleteById("P1");
        verify(springRepository).deleteById("P1");
    }
}
