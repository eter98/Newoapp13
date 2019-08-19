package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

import io.github.jhipster.newoapp13.domain.enumeration.TipoEntradad;

import io.github.jhipster.newoapp13.domain.enumeration.TipoIngresod;

/**
 * A EntradaMiembros.
 */
@Entity
@Table(name = "entrada_miembros")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "entradamiembros")
public class EntradaMiembros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "registro_fecha", nullable = false)
    private ZonedDateTime registroFecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_entrada")
    private TipoEntradad tipoEntrada;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ingreso")
    private TipoIngresod tipoIngreso;

    @Column(name = "tiempo_maximo")
    private Boolean tiempoMaximo;

    @ManyToOne
    @JsonIgnoreProperties("entradaMiembros")
    private Miembros miembro;

    @ManyToOne
    @JsonIgnoreProperties("entradaMiembros")
    private EspacioLibre espacio;

    @ManyToOne
    @JsonIgnoreProperties("entradaMiembros")
    private EspaciosReserva espacioReserva;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getRegistroFecha() {
        return registroFecha;
    }

    public EntradaMiembros registroFecha(ZonedDateTime registroFecha) {
        this.registroFecha = registroFecha;
        return this;
    }

    public void setRegistroFecha(ZonedDateTime registroFecha) {
        this.registroFecha = registroFecha;
    }

    public TipoEntradad getTipoEntrada() {
        return tipoEntrada;
    }

    public EntradaMiembros tipoEntrada(TipoEntradad tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
        return this;
    }

    public void setTipoEntrada(TipoEntradad tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public TipoIngresod getTipoIngreso() {
        return tipoIngreso;
    }

    public EntradaMiembros tipoIngreso(TipoIngresod tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
        return this;
    }

    public void setTipoIngreso(TipoIngresod tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public Boolean isTiempoMaximo() {
        return tiempoMaximo;
    }

    public EntradaMiembros tiempoMaximo(Boolean tiempoMaximo) {
        this.tiempoMaximo = tiempoMaximo;
        return this;
    }

    public void setTiempoMaximo(Boolean tiempoMaximo) {
        this.tiempoMaximo = tiempoMaximo;
    }

    public Miembros getMiembro() {
        return miembro;
    }

    public EntradaMiembros miembro(Miembros miembros) {
        this.miembro = miembros;
        return this;
    }

    public void setMiembro(Miembros miembros) {
        this.miembro = miembros;
    }

    public EspacioLibre getEspacio() {
        return espacio;
    }

    public EntradaMiembros espacio(EspacioLibre espacioLibre) {
        this.espacio = espacioLibre;
        return this;
    }

    public void setEspacio(EspacioLibre espacioLibre) {
        this.espacio = espacioLibre;
    }

    public EspaciosReserva getEspacioReserva() {
        return espacioReserva;
    }

    public EntradaMiembros espacioReserva(EspaciosReserva espaciosReserva) {
        this.espacioReserva = espaciosReserva;
        return this;
    }

    public void setEspacioReserva(EspaciosReserva espaciosReserva) {
        this.espacioReserva = espaciosReserva;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntradaMiembros)) {
            return false;
        }
        return id != null && id.equals(((EntradaMiembros) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EntradaMiembros{" +
            "id=" + getId() +
            ", registroFecha='" + getRegistroFecha() + "'" +
            ", tipoEntrada='" + getTipoEntrada() + "'" +
            ", tipoIngreso='" + getTipoIngreso() + "'" +
            ", tiempoMaximo='" + isTiempoMaximo() + "'" +
            "}";
    }
}
