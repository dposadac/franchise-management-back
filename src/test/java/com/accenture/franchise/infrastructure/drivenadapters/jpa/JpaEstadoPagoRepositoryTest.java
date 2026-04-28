package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.EstadoPago;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaEstadoPagoRepositoryTest {

    @Mock private JpaEstadoPagoRepositorySpring springRepository;

    private JpaEstadoPagoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new JpaEstadoPagoRepository(springRepository);
    }

    @Test
    void save_persistsAndReturnsMappedDomain() {
        EstadoPago domain = new EstadoPago("EP1", "Activo");
        EstadoPagoEntity entity = new EstadoPagoEntity("EP1", "Activo");
        when(springRepository.save(any(EstadoPagoEntity.class))).thenReturn(entity);

        EstadoPago result = repository.save(domain);

        assertThat(result.getIdEstado()).isEqualTo("EP1");
        assertThat(result.getNombre()).isEqualTo("Activo");
        verify(springRepository).save(any(EstadoPagoEntity.class));
    }

    @Test
    void findById_existing_returnsMappedDomain() {
        when(springRepository.findById("EP1")).thenReturn(Optional.of(new EstadoPagoEntity("EP1", "Activo")));

        Optional<EstadoPago> result = repository.findById("EP1");

        assertThat(result).isPresent();
        assertThat(result.get().getIdEstado()).isEqualTo("EP1");
    }

    @Test
    void findById_notFound_returnsEmpty() {
        when(springRepository.findById("NOPE")).thenReturn(Optional.empty());

        assertThat(repository.findById("NOPE")).isEmpty();
    }

    @Test
    void findAll_returnsMappedList() {
        when(springRepository.findAll()).thenReturn(List.of(
                new EstadoPagoEntity("EP1", "Activo"),
                new EstadoPagoEntity("EP2", "Inactivo")
        ));

        List<EstadoPago> result = repository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(1).getNombre()).isEqualTo("Inactivo");
    }

    @Test
    void deleteById_delegatesToSpring() {
        repository.deleteById("EP1");
        verify(springRepository).deleteById("EP1");
    }
}
