package com.accenture.franchise.domain.model;

public class EstadoPago {

    private String idEstado;
    private String nombre;

    public EstadoPago(String idEstado, String nombre) {
        this.idEstado = idEstado;
        this.nombre = nombre;
    }

    public String getIdEstado() { return idEstado; }
    public String getNombre() { return nombre; }
}
