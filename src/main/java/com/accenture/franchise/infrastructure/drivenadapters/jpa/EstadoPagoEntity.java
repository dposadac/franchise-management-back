package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "EstadoPago")
public class EstadoPagoEntity {

    @Id
    @Column(name = "IdEstado", length = 36, nullable = false)
    private String idEstado;

    @Column(name = "Nombre", length = 80, nullable = false)
    private String nombre;

    protected EstadoPagoEntity() {}

    public EstadoPagoEntity(String idEstado, String nombre) {
        this.idEstado = idEstado;
        this.nombre = nombre;
    }

    public String getIdEstado() { return idEstado; }
    public String getNombre() { return nombre; }
}
