package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

import io.github.jhipster.newoapp13.domain.enumeration.EstadoReservad;

/**
 * A Reservas.
 */
@Entity
@Table(name = "reservas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "reservas")
public class Reservas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "registro_fecha_entrada", nullable = false)
    private ZonedDateTime registroFechaEntrada;

    @NotNull
    @Column(name = "registro_fecha_salida", nullable = false)
    private ZonedDateTime registroFechaSalida;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_reserva")
    private EstadoReservad estadoReserva;

    @ManyToOne
    @JsonIgnoreProperties("reservas")
    private Miembros miembro;

    @ManyToOne
    @JsonIgnoreProperties("reservas")
    private EspaciosReserva espacio;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getRegistroFechaEntrada() {
        return registroFechaEntrada;
    }

    public Reservas registroFechaEntrada(ZonedDateTime registroFechaEntrada) {
        this.registroFechaEntrada = registroFechaEntrada;
        return this;
    }

    public void setRegistroFechaEntrada(ZonedDateTime registroFechaEntrada) {
        this.registroFechaEntrada = registroFechaEntrada;
    }

    public ZonedDateTime getRegistroFechaSalida() {
        return registroFechaSalida;
    }

    public Reservas registroFechaSalida(ZonedDateTime registroFechaSalida) {
        this.registroFechaSalida = registroFechaSalida;
        return this;
    }

    public void setRegistroFechaSalida(ZonedDateTime registroFechaSalida) {
        this.registroFechaSalida = registroFechaSalida;
    }

    public EstadoReservad getEstadoReserva() {
        return estadoReserva;
    }

    public Reservas estadoReserva(EstadoReservad estadoReserva) {
        this.estadoReserva = estadoReserva;
        return this;
    }

    public void setEstadoReserva(EstadoReservad estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public Miembros getMiembro() {
        return miembro;
    }

    public Reservas miembro(Miembros miembros) {
        this.miembro = miembros;
        return this;
    }

    public void setMiembro(Miembros miembros) {
        this.miembro = miembros;
    }

    public EspaciosReserva getEspacio() {
        return espacio;
    }

    public Reservas espacio(EspaciosReserva espaciosReserva) {
        this.espacio = espaciosReserva;
        return this;
    }

    public void setEspacio(EspaciosReserva espaciosReserva) {
        this.espacio = espaciosReserva;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservas)) {
            return false;
        }
        return id != null && id.equals(((Reservas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Reservas{" +
            "id=" + getId() +
            ", registroFechaEntrada='" + getRegistroFechaEntrada() + "'" +
            ", registroFechaSalida='" + getRegistroFechaSalida() + "'" +
            ", estadoReserva='" + getEstadoReserva() + "'" +
            "}";
    }
}
