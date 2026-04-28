package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.Sucursal;
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
class JpaSucursalRepositoryTest {

    @Mock private JpaSucursalRepositorySpring springRepository;

    private JpaSucursalRepository repository;

    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        repository = new JpaSucursalRepository(springRepository);
    }

    @Test
    void save_persistsAndReturnsMappedDomain() {
        Sucursal domain = new Sucursal(UUID.randomUUID().toString(), "Sucursal Norte", now, now);
        SucursalEntity entity = new SucursalEntity("S1", "Sucursal Norte", now, now);
        when(springRepository.save(any(SucursalEntity.class))).thenReturn(entity);

        Sucursal result = repository.save(domain);

        assertThat(result.getIdSucursal()).isEqualTo("S1");
        assertThat(result.getNombre()).isEqualTo("Sucursal Norte");
        verify(springRepository).save(any(SucursalEntity.class));
    }

    @Test
    void findById_existing_returnsMappedDomain() {
        when(springRepository.findById("S1"))
                .thenReturn(Optional.of(new SucursalEntity("S1", "Sucursal Norte", now, now)));

        Optional<Sucursal> result = repository.findById("S1");

        assertThat(result).isPresent();
        assertThat(result.get().getIdSucursal()).isEqualTo("S1");
    }

    @Test
    void findById_notFound_returnsEmpty() {
        when(springRepository.findById("NOPE")).thenReturn(Optional.empty());

        assertThat(repository.findById("NOPE")).isEmpty();
    }

    @Test
    void findAll_returnsMappedList() {
        when(springRepository.findAll()).thenReturn(List.of(
                new SucursalEntity("S1", "Norte", now, now),
                new SucursalEntity("S2", "Sur", now, now)
        ));

        List<Sucursal> result = repository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(1).getNombre()).isEqualTo("Sur");
    }

    @Test
    void deleteById_delegatesToSpring() {
        repository.deleteById("S1");
        verify(springRepository).deleteById("S1");
    }
}
