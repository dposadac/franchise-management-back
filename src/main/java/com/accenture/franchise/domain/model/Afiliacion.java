package com.accenture.franchise.domain.model;

import java.time.LocalDateTime;

public class Afiliacion {

    private String idSucursal;
    private String idFranquicia;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Afiliacion(String idSucursal, String idFranquicia,
                      LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.idSucursal = idSucursal;
        this.idFranquicia = idFranquicia;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getIdSucursal() { return idSucursal; }
    public String getIdFranquicia() { return idFranquicia; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
}
