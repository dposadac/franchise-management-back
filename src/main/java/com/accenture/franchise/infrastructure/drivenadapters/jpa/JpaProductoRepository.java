package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.Producto;
import com.accenture.franchise.domain.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

public class JpaProductoRepository implements ProductoRepository {

    private final JpaProductoRepositorySpring springRepository;

    public JpaProductoRepository(JpaProductoRepositorySpring springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Producto save(Producto producto) {
        return toDomain(springRepository.save(toEntity(producto)));
    }

    @Override
    public Optional<Producto> findById(String idProducto) {
        return springRepository.findById(idProducto).map(this::toDomain);
    }

    @Override
    public List<Producto> findAll() {
        return springRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(String idProducto) {
        springRepository.deleteById(idProducto);
    }

    private ProductoEntity toEntity(Producto producto) {
        return new ProductoEntity(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.isActivo(),
                producto.getFechaCreacion(),
                producto.getFechaActualizacion()
        );
    }

    private Producto toDomain(ProductoEntity entity) {
        return new Producto(
                entity.getIdProducto(),
                entity.getNombre(),
                entity.isActivo(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }
}
