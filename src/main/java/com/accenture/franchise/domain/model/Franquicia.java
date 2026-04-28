package com.accenture.franchise.domain.model;

import java.time.LocalDateTime;

public class Franquicia {

    private String idFranquicia;
    private String nombre;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Franquicia(String idFranquicia, String nombre,
                      LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.idFranquicia = idFranquicia;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getIdFranquicia() { return idFranquicia; }
    public String getNombre() { return nombre; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
}
