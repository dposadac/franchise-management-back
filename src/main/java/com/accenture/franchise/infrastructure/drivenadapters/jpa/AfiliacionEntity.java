package com.accenture.franchise.infrastructure.drivenadapters.jpa;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Afiliacion")
public class AfiliacionEntity {

    @EmbeddedId
    private AfiliacionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idSucursal")
    @JoinColumn(name = "IdSucursal", nullable = false)
    private SucursalEntity sucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idFranquicia")
    @JoinColumn(name = "IdFranquicia", nullable = false)
    private FranquiciaEntity franquicia;

    @Column(name = "FechaCreacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FechaActualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    protected AfiliacionEntity() {}

    public AfiliacionEntity(SucursalEntity sucursal, FranquiciaEntity franquicia,
                             LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = new AfiliacionId(sucursal.getIdSucursal(), franquicia.getIdFranquicia());
        this.sucursal = sucursal;
        this.franquicia = franquicia;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public AfiliacionId getId() { return id; }
    public SucursalEntity getSucursal() { return sucursal; }
    public FranquiciaEntity getFranquicia() { return franquicia; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }

    @Embeddable
    public static class AfiliacionId implements Serializable {

        @Column(name = "IdSucursal", columnDefinition = "UUID", nullable = false)
        private String idSucursal;

        @Column(name = "IdFranquicia", columnDefinition = "UUID", nullable = false)
        private String idFranquicia;

        protected AfiliacionId() {}

        public AfiliacionId(String idSucursal, String idFranquicia) {
            this.idSucursal = idSucursal;
            this.idFranquicia = idFranquicia;
        }

        public String getIdSucursal() { return idSucursal; }
        public String getIdFranquicia() { return idFranquicia; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AfiliacionId that)) return false;
            return Objects.equals(idSucursal, that.idSucursal) && Objects.equals(idFranquicia, that.idFranquicia);
        }

        @Override
        public int hashCode() { return Objects.hash(idSucursal, idFranquicia); }
    }
}
