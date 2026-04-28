package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Producto")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "IdProducto", length = 36, nullable = false, updatable = false)
    private String idProducto;

    @Column(name = "Nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "Activo", nullable = false)
    private boolean activo;

    @Column(name = "FechaCreacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaActualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    protected ProductoEntity() {}

    public ProductoEntity(String idProducto, String nombre, boolean activo, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getIdProducto() { return idProducto; }
    public String getNombre() { return nombre; }
    public boolean isActivo() { return activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
}
