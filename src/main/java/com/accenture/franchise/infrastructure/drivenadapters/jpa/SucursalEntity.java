package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Sucursal")
public class SucursalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "IdSucursal", length = 36, nullable = false, updatable = false)
    private String idSucursal;

    @Column(name = "Nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "FechaCreacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaActualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    protected SucursalEntity() {}

    public SucursalEntity(String idSucursal, String nombre, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getIdSucursal() { return idSucursal; }
    public String getNombre() { return nombre; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
}
