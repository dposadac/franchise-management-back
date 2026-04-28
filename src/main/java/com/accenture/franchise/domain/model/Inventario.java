package com.accenture.franchise.domain.model;

import java.time.LocalDateTime;

public class Inventario {

    private String idSucursal;
    private String idProducto;
    private int cantidadStock;
    private String idEstadoPago;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Inventario(String idSucursal, String idProducto, int cantidadStock, String idEstadoPago,
                      LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.idSucursal = idSucursal;
        this.idProducto = idProducto;
        this.cantidadStock = cantidadStock;
        this.idEstadoPago = idEstadoPago;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getIdSucursal() { return idSucursal; }
    public String getIdProducto() { return idProducto; }
    public int getCantidadStock() { return cantidadStock; }
    public String getIdEstadoPago() { return idEstadoPago; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }

    public void setCantidadStock(int cantidadStock) { this.cantidadStock = cantidadStock; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
