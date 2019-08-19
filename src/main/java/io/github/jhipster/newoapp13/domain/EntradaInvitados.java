package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import io.github.jhipster.newoapp13.domain.enumeration.TipoEntradad;

import io.github.jhipster.newoapp13.domain.enumeration.TipoIngresod;

/**
 * A EntradaInvitados.
 */
@Entity
@Table(name = "entrada_invitados")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "entradainvitados")
public class EntradaInvitados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "registro_fecha")
    private Instant registroFecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_entrada")
    private TipoEntradad tipoEntrada;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ingreso")
    private TipoIngresod tipoIngreso;

    @Column(name = "tiempo_maximo")
    private Boolean tiempoMaximo;

    @ManyToOne
    @JsonIgnoreProperties("entradaInvitados")
    private EspacioLibre espacio;

    @ManyToOne
    @JsonIgnoreProperties("entradaInvitados")
    private Landing oficina;

    @ManyToOne
    @JsonIgnoreProperties("entradaInvitados")
    private EspaciosReserva espacioReserva;

    @ManyToOne
    @JsonIgnoreProperties("entradaInvitados")
    private Invitados invitado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRegistroFecha() {
        return registroFecha;
    }

    public EntradaInvitados registroFecha(Instant registroFecha) {
        this.registroFecha = registroFecha;
        return this;
    }

    public void setRegistroFecha(Instant registroFecha) {
        this.registroFecha = registroFecha;
    }

    public TipoEntradad getTipoEntrada() {
        return tipoEntrada;
    }

    public EntradaInvitados tipoEntrada(TipoEntradad tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
        return this;
    }

    public void setTipoEntrada(TipoEntradad tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public TipoIngresod getTipoIngreso() {
        return tipoIngreso;
    }

    public EntradaInvitados tipoIngreso(TipoIngresod tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
        return this;
    }

    public void setTipoIngreso(TipoIngresod tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public Boolean isTiempoMaximo() {
        return tiempoMaximo;
    }

    public EntradaInvitados tiempoMaximo(Boolean tiempoMaximo) {
        this.tiempoMaximo = tiempoMaximo;
        return this;
    }

    public void setTiempoMaximo(Boolean tiempoMaximo) {
        this.tiempoMaximo = tiempoMaximo;
    }

    public EspacioLibre getEspacio() {
        return espacio;
    }

    public EntradaInvitados espacio(EspacioLibre espacioLibre) {
        this.espacio = espacioLibre;
        return this;
    }

    public void setEspacio(EspacioLibre espacioLibre) {
        this.espacio = espacioLibre;
    }

    public Landing getOficina() {
        return oficina;
    }

    public EntradaInvitados oficina(Landing landing) {
        this.oficina = landing;
        return this;
    }

    public void setOficina(Landing landing) {
        this.oficina = landing;
    }

    public EspaciosReserva getEspacioReserva() {
        return espacioReserva;
    }

    public EntradaInvitados espacioReserva(EspaciosReserva espaciosReserva) {
        this.espacioReserva = espaciosReserva;
        return this;
    }

    public void setEspacioReserva(EspaciosReserva espaciosReserva) {
        this.espacioReserva = espaciosReserva;
    }

    public Invitados getInvitado() {
        return invitado;
    }

    public EntradaInvitados invitado(Invitados invitados) {
        this.invitado = invitados;
        return this;
    }

    public void setInvitado(Invitados invitados) {
        this.invitado = invitados;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntradaInvitados)) {
            return false;
        }
        return id != null && id.equals(((EntradaInvitados) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EntradaInvitados{" +
            "id=" + getId() +
            ", registroFecha='" + getRegistroFecha() + "'" +
            ", tipoEntrada='" + getTipoEntrada() + "'" +
            ", tipoIngreso='" + getTipoIngreso() + "'" +
            ", tiempoMaximo='" + isTiempoMaximo() + "'" +
            "}";
    }
}
