package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.EstadoReservad;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Reservas} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.ReservasResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /reservas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReservasCriteria implements Serializable, Criteria {
    /**
     * Class for filtering EstadoReservad
     */
    public static class EstadoReservadFilter extends Filter<EstadoReservad> {

        public EstadoReservadFilter() {
        }

        public EstadoReservadFilter(EstadoReservadFilter filter) {
            super(filter);
        }

        @Override
        public EstadoReservadFilter copy() {
            return new EstadoReservadFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter registroFechaEntrada;

    private ZonedDateTimeFilter registroFechaSalida;

    private EstadoReservadFilter estadoReserva;

    private LongFilter miembroId;

    private LongFilter espacioId;

    public ReservasCriteria(){
    }

    public ReservasCriteria(ReservasCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.registroFechaEntrada = other.registroFechaEntrada == null ? null : other.registroFechaEntrada.copy();
        this.registroFechaSalida = other.registroFechaSalida == null ? null : other.registroFechaSalida.copy();
        this.estadoReserva = other.estadoReserva == null ? null : other.estadoReserva.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
        this.espacioId = other.espacioId == null ? null : other.espacioId.copy();
    }

    @Override
    public ReservasCriteria copy() {
        return new ReservasCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getRegistroFechaEntrada() {
        return registroFechaEntrada;
    }

    public void setRegistroFechaEntrada(ZonedDateTimeFilter registroFechaEntrada) {
        this.registroFechaEntrada = registroFechaEntrada;
    }

    public ZonedDateTimeFilter getRegistroFechaSalida() {
        return registroFechaSalida;
    }

    public void setRegistroFechaSalida(ZonedDateTimeFilter registroFechaSalida) {
        this.registroFechaSalida = registroFechaSalida;
    }

    public EstadoReservadFilter getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReservadFilter estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public LongFilter getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(LongFilter miembroId) {
        this.miembroId = miembroId;
    }

    public LongFilter getEspacioId() {
        return espacioId;
    }

    public void setEspacioId(LongFilter espacioId) {
        this.espacioId = espacioId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReservasCriteria that = (ReservasCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(registroFechaEntrada, that.registroFechaEntrada) &&
            Objects.equals(registroFechaSalida, that.registroFechaSalida) &&
            Objects.equals(estadoReserva, that.estadoReserva) &&
            Objects.equals(miembroId, that.miembroId) &&
            Objects.equals(espacioId, that.espacioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        registroFechaEntrada,
        registroFechaSalida,
        estadoReserva,
        miembroId,
        espacioId
        );
    }

    @Override
    public String toString() {
        return "ReservasCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (registroFechaEntrada != null ? "registroFechaEntrada=" + registroFechaEntrada + ", " : "") +
                (registroFechaSalida != null ? "registroFechaSalida=" + registroFechaSalida + ", " : "") +
                (estadoReserva != null ? "estadoReserva=" + estadoReserva + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
                (espacioId != null ? "espacioId=" + espacioId + ", " : "") +
            "}";
    }

}
