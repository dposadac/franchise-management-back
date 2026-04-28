package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.Inventario;
import com.accenture.franchise.domain.repository.InventarioRepository;

import java.util.List;
import java.util.Optional;

public class JpaInventarioRepository implements InventarioRepository {

    private final JpaInventarioRepositorySpring springRepository;
    private final JpaSucursalRepositorySpring sucursalRepo;
    private final JpaProductoRepositorySpring productoRepo;
    private final JpaEstadoPagoRepositorySpring estadoPagoRepo;

    public JpaInventarioRepository(JpaInventarioRepositorySpring springRepository,
                                    JpaSucursalRepositorySpring sucursalRepo,
                                    JpaProductoRepositorySpring productoRepo,
                                    JpaEstadoPagoRepositorySpring estadoPagoRepo) {
        this.springRepository = springRepository;
        this.sucursalRepo = sucursalRepo;
        this.productoRepo = productoRepo;
        this.estadoPagoRepo = estadoPagoRepo;
    }

    @Override
    public Inventario save(Inventario inventario) {
        SucursalEntity sucursal = sucursalRepo.getReferenceById(inventario.getIdSucursal());
        ProductoEntity producto = productoRepo.getReferenceById(inventario.getIdProducto());
        EstadoPagoEntity estadoPago = estadoPagoRepo.getReferenceById(inventario.getIdEstadoPago());
        InventarioEntity entity = new InventarioEntity(sucursal, producto, inventario.getCantidadStock(),
                estadoPago, inventario.getFechaCreacion(), inventario.getFechaActualizacion());
        return toDomain(springRepository.save(entity));
    }

    @Override
    public Optional<Inventario> findById(String idSucursal, String idProducto) {
        InventarioEntity.InventarioId key = new InventarioEntity.InventarioId(idSucursal, idProducto);
        return springRepository.findById(key).map(this::toDomain);
    }

    @Override
    public List<Inventario> findAll() {
        return springRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String idSucursal, String idProducto) {
        springRepository.deleteById(new InventarioEntity.InventarioId(idSucursal, idProducto));
    }

    private Inventario toDomain(InventarioEntity entity) {
        return new Inventario(
                entity.getId().getIdSucursal(),
                entity.getId().getIdProducto(),
                entity.getCantidadStock(),
                entity.getEstadoPago().getIdEstado(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
