package com.accenture.franchise.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Producto {

    private String idProducto;
    private String nombre;
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Producto(String idProducto, String nombre, boolean activo,
                    LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
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
