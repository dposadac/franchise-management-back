package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.Afiliacion;
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
class JpaAfiliacionRepositoryTest {

    @Mock private JpaAfiliacionRepositorySpring springRepository;
    @Mock private JpaSucursalRepositorySpring sucursalRepo;
    @Mock private JpaFranquiciaRepositorySpring franquiciaRepo;

    private JpaAfiliacionRepository repository;

    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        repository = new JpaAfiliacionRepository(springRepository, sucursalRepo, franquiciaRepo);
    }

    @Test
    void save_resolvesReferencesAndPersists() {
        Afiliacion domain = new Afiliacion("S1", "F1", now, now);
        SucursalEntity sucursal = new SucursalEntity("S1", "Norte", now, now);
        FranquiciaEntity franquicia = new FranquiciaEntity("F1", "Central", now, now);
        AfiliacionEntity saved = new AfiliacionEntity(sucursal, franquicia, now, now);

        when(sucursalRepo.getReferenceById("S1")).thenReturn(sucursal);
        when(franquiciaRepo.getReferenceById("F1")).thenReturn(franquicia);
        when(springRepository.save(any(AfiliacionEntity.class))).thenReturn(saved);

        Afiliacion result = repository.save(domain);

        assertThat(result.getIdSucursal()).isEqualTo("S1");
        assertThat(result.getIdFranquicia()).isEqualTo("F1");
        verify(springRepository).save(any(AfiliacionEntity.class));
    }

    @Test
    void findById_existing_returnsMappedDomain() {
        SucursalEntity sucursal = new SucursalEntity("S1", "Norte", now, now);
        FranquiciaEntity franquicia = new FranquiciaEntity("F1", "Central", now, now);
        AfiliacionEntity entity = new AfiliacionEntity(sucursal, franquicia, now, now);
        AfiliacionEntity.AfiliacionId key = new AfiliacionEntity.AfiliacionId("S1", "F1");

        when(springRepository.findById(key)).thenReturn(Optional.of(entity));

        Optional<Afiliacion> result = repository.findById("S1", "F1");

        assertThat(result).isPresent();
        assertThat(result.get().getIdSucursal()).isEqualTo("S1");
        assertThat(result.get().getIdFranquicia()).isEqualTo("F1");
    }

    @Test
    void findById_notFound_returnsEmpty() {
        when(springRepository.findById(any(AfiliacionEntity.AfiliacionId.class))).thenReturn(Optional.empty());

        assertThat(repository.findById("S1", "F1")).isEmpty();
    }

    @Test
    void findAll_returnsMappedList() {
        SucursalEntity s1 = new SucursalEntity("S1", "Norte", now, now);
        FranquiciaEntity f1 = new FranquiciaEntity("F1", "Central", now, now);
        when(springRepository.findAll()).thenReturn(List.of(new AfiliacionEntity(s1, f1, now, now)));

        List<Afiliacion> result = repository.findAll();

        assertThat(result).hasSize(1);
    }

    @Test
    void deleteById_delegatesToSpring() {
        repository.deleteById("S1", "F1");
        verify(springRepository).deleteById(new AfiliacionEntity.AfiliacionId("S1", "F1"));
    }
}
