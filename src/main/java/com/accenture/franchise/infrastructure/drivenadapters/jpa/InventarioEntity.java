package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Inventario")
public class InventarioEntity {

    @EmbeddedId
    private InventarioId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idSucursal")
    @JoinColumn(name = "IdSucursal", nullable = false)
    private SucursalEntity sucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProducto")
    @JoinColumn(name = "IdProducto", nullable = false)
    private ProductoEntity producto;

    @Column(name = "CantidadStock", nullable = false)
    private int cantidadStock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdEstadoPago", nullable = false)
    private EstadoPagoEntity estadoPago;

    @Column(name = "FechaCreacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaActualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    protected InventarioEntity() {}

    public InventarioEntity(SucursalEntity sucursal, ProductoEntity producto, int cantidadStock,
                             EstadoPagoEntity estadoPago, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = new InventarioId(sucursal.getIdSucursal(), producto.getIdProducto());
        this.sucursal = sucursal;
        this.producto = producto;
        this.cantidadStock = cantidadStock;
        this.estadoPago = estadoPago;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public InventarioId getId() { return id; }
    public SucursalEntity getSucursal() { return sucursal; }
    public ProductoEntity getProducto() { return producto; }
    public int getCantidadStock() { return cantidadStock; }
    public EstadoPagoEntity getEstadoPago() { return estadoPago; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }

    @Embeddable
    public static class InventarioId implements Serializable {

        @Column(name = "IdSucursal", columnDefinition = "UUID", nullable = false)
        private String idSucursal;

        @Column(name = "IdProducto", columnDefinition = "UUID", nullable = false)
        private String idProducto;

        protected InventarioId() {}

        public InventarioId(String idSucursal, String idProducto) {
            this.idSucursal = idSucursal;
            this.idProducto = idProducto;
        }

        public String getIdSucursal() { return idSucursal; }
        public String getIdProducto() { return idProducto; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof InventarioId that)) return false;
            return Objects.equals(idSucursal, that.idSucursal) && Objects.equals(idProducto, that.idProducto);
        }

        @Override
        public int hashCode() { return Objects.hash(idSucursal, idProducto); }
    }
}
