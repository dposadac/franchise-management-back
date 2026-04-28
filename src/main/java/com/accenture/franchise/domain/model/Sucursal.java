package com.accenture.franchise.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Sucursal {

    private String idSucursal;
    private String nombre;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Sucursal(String idSucursal, String nombre,
                    LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
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
