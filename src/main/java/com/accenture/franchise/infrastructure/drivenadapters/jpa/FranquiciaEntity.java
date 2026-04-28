package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Franquicia")
public class FranquiciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "IdFranquicia", length = 36, nullable = false, updatable = false)
    private String idFranquicia;

    @Column(name = "Nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "FechaCreacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaActualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    protected FranquiciaEntity() {}

    public FranquiciaEntity(String idFranquicia, String nombre, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
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
