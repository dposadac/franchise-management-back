package com.accenture.franchise.domain.exception;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(String idSucursal, String idProducto) {
        super("Inventory not found with branchId: " + idSucursal + " and productId: " + idProducto);
    }
}
