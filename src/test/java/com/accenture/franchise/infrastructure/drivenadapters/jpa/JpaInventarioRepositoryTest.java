package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.Inventario;
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
class JpaInventarioRepositoryTest {

    @Mock private JpaInventarioRepositorySpring springRepository;
    @Mock private JpaSucursalRepositorySpring sucursalRepo;
    @Mock private JpaProductoRepositorySpring productoRepo;
    @Mock private JpaEstadoPagoRepositorySpring estadoPagoRepo;

    private JpaInventarioRepository repository;

    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        repository = new JpaInventarioRepository(springRepository, sucursalRepo, productoRepo, estadoPagoRepo);
    }

    @Test
    void save_resolvesReferencesAndPersists() {
        Inventario domain = new Inventario("S1", "P1", 50, "EP1", now, now);
        SucursalEntity sucursal = new SucursalEntity("S1", "Norte", now, now);
        ProductoEntity producto = new ProductoEntity("P1", "Hamburguesa", true, now, now);
        EstadoPagoEntity estadoPago = new EstadoPagoEntity("EP1", "Activo");
        InventarioEntity saved = new InventarioEntity(sucursal, producto, 50, estadoPago, now, now);

        when(sucursalRepo.getReferenceById("S1")).thenReturn(sucursal);
        when(productoRepo.getReferenceById("P1")).thenReturn(producto);
        when(estadoPagoRepo.getReferenceById("EP1")).thenReturn(estadoPago);
        when(springRepository.save(any(InventarioEntity.class))).thenReturn(saved);

        Inventario result = repository.save(domain);

        assertThat(result.getIdSucursal()).isEqualTo("S1");
        assertThat(result.getIdProducto()).isEqualTo("P1");
        assertThat(result.getCantidadStock()).isEqualTo(50);
        assertThat(result.getIdEstadoPago()).isEqualTo("EP1");
        verify(springRepository).save(any(InventarioEntity.class));
    }

    @Test
    void findById_existing_returnsMappedDomain() {
        SucursalEntity sucursal = new SucursalEntity("S1", "Norte", now, now);
        ProductoEntity producto = new ProductoEntity("P1", "Hamburguesa", true, now, now);
        EstadoPagoEntity estadoPago = new EstadoPagoEntity("EP1", "Activo");
        InventarioEntity entity = new InventarioEntity(sucursal, producto, 10, estadoPago, now, now);
        InventarioEntity.InventarioId key = new InventarioEntity.InventarioId("S1", "P1");

        when(springRepository.findById(key)).thenReturn(Optional.of(entity));

        Optional<Inventario> result = repository.findById("S1", "P1");

        assertThat(result).isPresent();
        assertThat(result.get().getCantidadStock()).isEqualTo(10);
        assertThat(result.get().getIdEstadoPago()).isEqualTo("EP1");
    }

    @Test
    void findById_notFound_returnsEmpty() {
        when(springRepository.findById(any(InventarioEntity.InventarioId.class))).thenReturn(Optional.empty());

        assertThat(repository.findById("S1", "P1")).isEmpty();
    }

    @Test
    void findAll_returnsMappedList() {
        SucursalEntity s = new SucursalEntity("S1", "Norte", now, now);
        ProductoEntity p = new ProductoEntity("P1", "Hamburguesa", true, now, now);
        EstadoPagoEntity ep = new EstadoPagoEntity("EP1", "Activo");
        when(springRepository.findAll()).thenReturn(List.of(new InventarioEntity(s, p, 5, ep, now, now)));

        List<Inventario> result = repository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCantidadStock()).isEqualTo(5);
    }

    @Test
    void deleteById_delegatesToSpring() {
        repository.deleteById("S1", "P1");
        verify(springRepository).deleteById(new InventarioEntity.InventarioId("S1", "P1"));
    }
}
