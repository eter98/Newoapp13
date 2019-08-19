package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.TipoEntradad;
import io.github.jhipster.newoapp13.domain.enumeration.TipoIngresod;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.EntradaInvitados} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.EntradaInvitadosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /entrada-invitados?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EntradaInvitadosCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoEntradad
     */
    public static class TipoEntradadFilter extends Filter<TipoEntradad> {

        public TipoEntradadFilter() {
        }

        public TipoEntradadFilter(TipoEntradadFilter filter) {
            super(filter);
        }

        @Override
        public TipoEntradadFilter copy() {
            return new TipoEntradadFilter(this);
        }

    }
    /**
     * Class for filtering TipoIngresod
     */
    public static class TipoIngresodFilter extends Filter<TipoIngresod> {

        public TipoIngresodFilter() {
        }

        public TipoIngresodFilter(TipoIngresodFilter filter) {
            super(filter);
        }

        @Override
        public TipoIngresodFilter copy() {
            return new TipoIngresodFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter registroFecha;

    private TipoEntradadFilter tipoEntrada;

    private TipoIngresodFilter tipoIngreso;

    private BooleanFilter tiempoMaximo;

    private LongFilter espacioId;

    private LongFilter espacioReservaId;

    private LongFilter invitadoId;

    private LongFilter miembroId;

    public EntradaInvitadosCriteria(){
    }

    public EntradaInvitadosCriteria(EntradaInvitadosCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.registroFecha = other.registroFecha == null ? null : other.registroFecha.copy();
        this.tipoEntrada = other.tipoEntrada == null ? null : other.tipoEntrada.copy();
        this.tipoIngreso = other.tipoIngreso == null ? null : other.tipoIngreso.copy();
        this.tiempoMaximo = other.tiempoMaximo == null ? null : other.tiempoMaximo.copy();
        this.espacioId = other.espacioId == null ? null : other.espacioId.copy();
        this.espacioReservaId = other.espacioReservaId == null ? null : other.espacioReservaId.copy();
        this.invitadoId = other.invitadoId == null ? null : other.invitadoId.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
    }

    @Override
    public EntradaInvitadosCriteria copy() {
        return new EntradaInvitadosCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getRegistroFecha() {
        return registroFecha;
    }

    public void setRegistroFecha(ZonedDateTimeFilter registroFecha) {
        this.registroFecha = registroFecha;
    }

    public TipoEntradadFilter getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(TipoEntradadFilter tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public TipoIngresodFilter getTipoIngreso() {
        return tipoIngreso;
    }

    public void setTipoIngreso(TipoIngresodFilter tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public BooleanFilter getTiempoMaximo() {
        return tiempoMaximo;
    }

    public void setTiempoMaximo(BooleanFilter tiempoMaximo) {
        this.tiempoMaximo = tiempoMaximo;
    }

    public LongFilter getEspacioId() {
        return espacioId;
    }

    public void setEspacioId(LongFilter espacioId) {
        this.espacioId = espacioId;
    }

    public LongFilter getEspacioReservaId() {
        return espacioReservaId;
    }

    public void setEspacioReservaId(LongFilter espacioReservaId) {
        this.espacioReservaId = espacioReservaId;
    }

    public LongFilter getInvitadoId() {
        return invitadoId;
    }

    public void setInvitadoId(LongFilter invitadoId) {
        this.invitadoId = invitadoId;
    }

    public LongFilter getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(LongFilter miembroId) {
        this.miembroId = miembroId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EntradaInvitadosCriteria that = (EntradaInvitadosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(registroFecha, that.registroFecha) &&
            Objects.equals(tipoEntrada, that.tipoEntrada) &&
            Objects.equals(tipoIngreso, that.tipoIngreso) &&
            Objects.equals(tiempoMaximo, that.tiempoMaximo) &&
            Objects.equals(espacioId, that.espacioId) &&
            Objects.equals(espacioReservaId, that.espacioReservaId) &&
            Objects.equals(invitadoId, that.invitadoId) &&
            Objects.equals(miembroId, that.miembroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        registroFecha,
        tipoEntrada,
        tipoIngreso,
        tiempoMaximo,
        espacioId,
        espacioReservaId,
        invitadoId,
        miembroId
        );
    }

    @Override
    public String toString() {
        return "EntradaInvitadosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (registroFecha != null ? "registroFecha=" + registroFecha + ", " : "") +
                (tipoEntrada != null ? "tipoEntrada=" + tipoEntrada + ", " : "") +
                (tipoIngreso != null ? "tipoIngreso=" + tipoIngreso + ", " : "") +
                (tiempoMaximo != null ? "tiempoMaximo=" + tiempoMaximo + ", " : "") +
                (espacioId != null ? "espacioId=" + espacioId + ", " : "") +
                (espacioReservaId != null ? "espacioReservaId=" + espacioReservaId + ", " : "") +
                (invitadoId != null ? "invitadoId=" + invitadoId + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
            "}";
    }

}
