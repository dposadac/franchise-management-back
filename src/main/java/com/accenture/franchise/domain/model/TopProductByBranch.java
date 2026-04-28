package com.accenture.franchise.domain.model;

public class TopProductByBranch {

    private final String idFranquicia;
    private final String idSucursal;
    private final String nombreFranquicia;
    private final String nombreSucursal;
    private final String nombreProducto;
    private final int cantidadStock;

    public TopProductByBranch(String idFranquicia, String idSucursal, String nombreFranquicia,
                               String nombreSucursal, String nombreProducto, int cantidadStock) {
        this.idFranquicia = idFranquicia;
        this.idSucursal = idSucursal;
        this.nombreFranquicia = nombreFranquicia;
        this.nombreSucursal = nombreSucursal;
        this.nombreProducto = nombreProducto;
        this.cantidadStock = cantidadStock;
    }

    public String getIdFranquicia() { return idFranquicia; }
    public String getIdSucursal() { return idSucursal; }
    public String getNombreFranquicia() { return nombreFranquicia; }
    public String getNombreSucursal() { return nombreSucursal; }
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidadStock() { return cantidadStock; }
}
