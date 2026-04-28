package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import com.accenture.franchise.domain.model.TopProductByBranch;
import com.accenture.franchise.domain.repository.TopProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;

import java.util.List;

public class JpaTopProductRepository implements TopProductRepository {

    private final EntityManager entityManager;

    public JpaTopProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<TopProductByBranch> findTopProductsByFranchiseAndBranch(String idFranquicia, String idSucursal) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();

        // FROM Afiliacion a
        Root<AfiliacionEntity> afiliacion = cq.from(AfiliacionEntity.class);

        // INNER JOIN Franquicia f ON f.IdFranquicia = a.IdFranquicia
        Join<AfiliacionEntity, FranquiciaEntity> franquicia = afiliacion.join("franquicia", JoinType.INNER);

        // INNER JOIN Sucursal s ON s.IdSucursal = a.IdSucursal
        Join<AfiliacionEntity, SucursalEntity> sucursal = afiliacion.join("sucursal", JoinType.INNER);

        // INNER JOIN Inventario i ON i.IdSucursal = s.IdSucursal
        Root<InventarioEntity> inventario = cq.from(InventarioEntity.class);

        // INNER JOIN Producto p ON p.IdProducto = i.IdProducto
        Join<InventarioEntity, ProductoEntity> producto = inventario.join("producto", JoinType.INNER);
        Join<InventarioEntity, SucursalEntity> inventarioSucursal = inventario.join("sucursal", JoinType.INNER);

        cq.multiselect(
                afiliacion.get("id").get("idFranquicia").alias("idFranquicia"),
                afiliacion.get("id").get("idSucursal").alias("idSucursal"),
                franquicia.get("nombre").alias("nombreFranquicia"),
                sucursal.get("nombre").alias("nombreSucursal"),
                producto.get("nombre").alias("nombreProducto"),
                inventario.get("cantidadStock").alias("cantidadStock")
        );

        Predicate franchiseFilter = cb.equal(franquicia.get("idFranquicia"), idFranquicia);
        Predicate branchFilter = cb.equal(sucursal.get("idSucursal"), idSucursal);
        Predicate inventoryJoin = cb.equal(
                inventarioSucursal.get("idSucursal"),
                sucursal.get("idSucursal")
        );
        cq.where(cb.and(franchiseFilter, branchFilter, inventoryJoin));

        // GROUP BY
        cq.groupBy(
                afiliacion.get("id").get("idFranquicia"),
                afiliacion.get("id").get("idSucursal"),
                franquicia.get("nombre"),
                sucursal.get("nombre"),
                producto.get("nombre"),
                inventario.get("cantidadStock")
        );

        Subquery<Integer> maxStockSubquery = cq.subquery(Integer.class);
        Root<InventarioEntity> subInventario = maxStockSubquery.from(InventarioEntity.class);
        maxStockSubquery.select(cb.max(subInventario.get("cantidadStock")));

        cq.having(cb.equal(inventario.get("cantidadStock"), maxStockSubquery));

        return entityManager.createQuery(cq).getResultList().stream()
                .map(tuple -> new TopProductByBranch(
                        tuple.get("idFranquicia", String.class),
                        tuple.get("idSucursal", String.class),
                        tuple.get("nombreFranquicia", String.class),
                        tuple.get("nombreSucursal", String.class),
                        tuple.get("nombreProducto", String.class),
                        tuple.get("cantidadStock", Integer.class)
                ))
                .toList();
    }
}
