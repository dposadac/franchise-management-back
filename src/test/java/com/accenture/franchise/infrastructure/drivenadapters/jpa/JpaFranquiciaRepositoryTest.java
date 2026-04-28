package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.Franquicia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaFranquiciaRepositoryTest {

    @Mock private JpaFranquiciaRepositorySpring springRepository;

    private JpaFranquiciaRepository repository;

    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        repository = new JpaFranquiciaRepository(springRepository);
    }

    @Test
    void save_persistsAndReturnsMappedDomain() {
        Franquicia domain = new Franquicia("F1", "Franquicia Central", now, now);
        FranquiciaEntity entity = new FranquiciaEntity("F1", "Franquicia Central", now, now);
        when(springRepository.save(any(FranquiciaEntity.class))).thenReturn(entity);

        Franquicia result = repository.save(domain);

        assertThat(result.getIdFranquicia()).isEqualTo("F1");
        assertThat(result.getNombre()).isEqualTo("Franquicia Central");
        verify(springRepository).save(any(FranquiciaEntity.class));
    }

    @Test
    void findById_existing_returnsMappedDomain() {
        when(springRepository.findById("F1"))
                .thenReturn(Optional.of(new FranquiciaEntity("F1", "Franquicia Central", now, now)));

        Optional<Franquicia> result = repository.findById("F1");

        assertThat(result).isPresent();
        assertThat(result.get().getIdFranquicia()).isEqualTo("F1");
    }

    @Test
    void findById_notFound_returnsEmpty() {
        when(springRepository.findById("NOPE")).thenReturn(Optional.empty());

        assertThat(repository.findById("NOPE")).isEmpty();
    }

    @Test
    void findAll_returnsMappedList() {
        when(springRepository.findAll()).thenReturn(List.of(
                new FranquiciaEntity("F1", "Central", now, now),
                new FranquiciaEntity("F2", "Norte", now, now)
        ));

        List<Franquicia> result = repository.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(1).getNombre()).isEqualTo("Norte");
    }

    @Test
    void deleteById_delegatesToSpring() {
        repository.deleteById("F1");
        verify(springRepository).deleteById("F1");
    }
}
