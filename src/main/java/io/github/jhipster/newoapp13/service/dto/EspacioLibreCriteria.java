package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.EspacioLibre} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.EspacioLibreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /espacio-libres?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EspacioLibreCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Impuestod
     */
    public static class ImpuestodFilter extends Filter<Impuestod> {

        public ImpuestodFilter() {
        }

        public ImpuestodFilter(ImpuestodFilter filter) {
            super(filter);
        }

        @Override
        public ImpuestodFilter copy() {
            return new ImpuestodFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private IntegerFilter capacidadInstalada;

    private StringFilter wifi;

    private IntegerFilter tarifa1hMiembro;

    private IntegerFilter tarifa2hMiembro;

    private IntegerFilter tarifa3hMiembro;

    private IntegerFilter tarifa4hMiembro;

    private IntegerFilter tarifa5hMiembro;

    private IntegerFilter tarifa6hMiembro;

    private IntegerFilter tarifa7hMiembro;

    private IntegerFilter tarifa8hMiembro;

    private IntegerFilter tarifa1hInvitado;

    private IntegerFilter tarifa2hInvitado;

    private IntegerFilter tarifa3hInvitado;

    private IntegerFilter tarifa4hInvitado;

    private IntegerFilter tarifa5hInvitado;

    private IntegerFilter tarifa6hInvitado;

    private IntegerFilter tarifa7hInvitado;

    private IntegerFilter tarifa8hInvitado;

    private ImpuestodFilter impuesto;

    private LongFilter sedeId;

    private LongFilter tipoEspacioId;

    public EspacioLibreCriteria(){
    }

    public EspacioLibreCriteria(EspacioLibreCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.capacidadInstalada = other.capacidadInstalada == null ? null : other.capacidadInstalada.copy();
        this.wifi = other.wifi == null ? null : other.wifi.copy();
        this.tarifa1hMiembro = other.tarifa1hMiembro == null ? null : other.tarifa1hMiembro.copy();
        this.tarifa2hMiembro = other.tarifa2hMiembro == null ? null : other.tarifa2hMiembro.copy();
        this.tarifa3hMiembro = other.tarifa3hMiembro == null ? null : other.tarifa3hMiembro.copy();
        this.tarifa4hMiembro = other.tarifa4hMiembro == null ? null : other.tarifa4hMiembro.copy();
        this.tarifa5hMiembro = other.tarifa5hMiembro == null ? null : other.tarifa5hMiembro.copy();
        this.tarifa6hMiembro = other.tarifa6hMiembro == null ? null : other.tarifa6hMiembro.copy();
        this.tarifa7hMiembro = other.tarifa7hMiembro == null ? null : other.tarifa7hMiembro.copy();
        this.tarifa8hMiembro = other.tarifa8hMiembro == null ? null : other.tarifa8hMiembro.copy();
        this.tarifa1hInvitado = other.tarifa1hInvitado == null ? null : other.tarifa1hInvitado.copy();
        this.tarifa2hInvitado = other.tarifa2hInvitado == null ? null : other.tarifa2hInvitado.copy();
        this.tarifa3hInvitado = other.tarifa3hInvitado == null ? null : other.tarifa3hInvitado.copy();
        this.tarifa4hInvitado = other.tarifa4hInvitado == null ? null : other.tarifa4hInvitado.copy();
        this.tarifa5hInvitado = other.tarifa5hInvitado == null ? null : other.tarifa5hInvitado.copy();
        this.tarifa6hInvitado = other.tarifa6hInvitado == null ? null : other.tarifa6hInvitado.copy();
        this.tarifa7hInvitado = other.tarifa7hInvitado == null ? null : other.tarifa7hInvitado.copy();
        this.tarifa8hInvitado = other.tarifa8hInvitado == null ? null : other.tarifa8hInvitado.copy();
        this.impuesto = other.impuesto == null ? null : other.impuesto.copy();
        this.sedeId = other.sedeId == null ? null : other.sedeId.copy();
        this.tipoEspacioId = other.tipoEspacioId == null ? null : other.tipoEspacioId.copy();
    }

    @Override
    public EspacioLibreCriteria copy() {
        return new EspacioLibreCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public IntegerFilter getCapacidadInstalada() {
        return capacidadInstalada;
    }

    public void setCapacidadInstalada(IntegerFilter capacidadInstalada) {
        this.capacidadInstalada = capacidadInstalada;
    }

    public StringFilter getWifi() {
        return wifi;
    }

    public void setWifi(StringFilter wifi) {
        this.wifi = wifi;
    }

    public IntegerFilter getTarifa1hMiembro() {
        return tarifa1hMiembro;
    }

    public void setTarifa1hMiembro(IntegerFilter tarifa1hMiembro) {
        this.tarifa1hMiembro = tarifa1hMiembro;
    }

    public IntegerFilter getTarifa2hMiembro() {
        return tarifa2hMiembro;
    }

    public void setTarifa2hMiembro(IntegerFilter tarifa2hMiembro) {
        this.tarifa2hMiembro = tarifa2hMiembro;
    }

    public IntegerFilter getTarifa3hMiembro() {
        return tarifa3hMiembro;
    }

    public void setTarifa3hMiembro(IntegerFilter tarifa3hMiembro) {
        this.tarifa3hMiembro = tarifa3hMiembro;
    }

    public IntegerFilter getTarifa4hMiembro() {
        return tarifa4hMiembro;
    }

    public void setTarifa4hMiembro(IntegerFilter tarifa4hMiembro) {
        this.tarifa4hMiembro = tarifa4hMiembro;
    }

    public IntegerFilter getTarifa5hMiembro() {
        return tarifa5hMiembro;
    }

    public void setTarifa5hMiembro(IntegerFilter tarifa5hMiembro) {
        this.tarifa5hMiembro = tarifa5hMiembro;
    }

    public IntegerFilter getTarifa6hMiembro() {
        return tarifa6hMiembro;
    }

    public void setTarifa6hMiembro(IntegerFilter tarifa6hMiembro) {
        this.tarifa6hMiembro = tarifa6hMiembro;
    }

    public IntegerFilter getTarifa7hMiembro() {
        return tarifa7hMiembro;
    }

    public void setTarifa7hMiembro(IntegerFilter tarifa7hMiembro) {
        this.tarifa7hMiembro = tarifa7hMiembro;
    }

    public IntegerFilter getTarifa8hMiembro() {
        return tarifa8hMiembro;
    }

    public void setTarifa8hMiembro(IntegerFilter tarifa8hMiembro) {
        this.tarifa8hMiembro = tarifa8hMiembro;
    }

    public IntegerFilter getTarifa1hInvitado() {
        return tarifa1hInvitado;
    }

    public void setTarifa1hInvitado(IntegerFilter tarifa1hInvitado) {
        this.tarifa1hInvitado = tarifa1hInvitado;
    }

    public IntegerFilter getTarifa2hInvitado() {
        return tarifa2hInvitado;
    }

    public void setTarifa2hInvitado(IntegerFilter tarifa2hInvitado) {
        this.tarifa2hInvitado = tarifa2hInvitado;
    }

    public IntegerFilter getTarifa3hInvitado() {
        return tarifa3hInvitado;
    }

    public void setTarifa3hInvitado(IntegerFilter tarifa3hInvitado) {
        this.tarifa3hInvitado = tarifa3hInvitado;
    }

    public IntegerFilter getTarifa4hInvitado() {
        return tarifa4hInvitado;
    }

    public void setTarifa4hInvitado(IntegerFilter tarifa4hInvitado) {
        this.tarifa4hInvitado = tarifa4hInvitado;
    }

    public IntegerFilter getTarifa5hInvitado() {
        return tarifa5hInvitado;
    }

    public void setTarifa5hInvitado(IntegerFilter tarifa5hInvitado) {
        this.tarifa5hInvitado = tarifa5hInvitado;
    }

    public IntegerFilter getTarifa6hInvitado() {
        return tarifa6hInvitado;
    }

    public void setTarifa6hInvitado(IntegerFilter tarifa6hInvitado) {
        this.tarifa6hInvitado = tarifa6hInvitado;
    }

    public IntegerFilter getTarifa7hInvitado() {
        return tarifa7hInvitado;
    }

    public void setTarifa7hInvitado(IntegerFilter tarifa7hInvitado) {
        this.tarifa7hInvitado = tarifa7hInvitado;
    }

    public IntegerFilter getTarifa8hInvitado() {
        return tarifa8hInvitado;
    }

    public void setTarifa8hInvitado(IntegerFilter tarifa8hInvitado) {
        this.tarifa8hInvitado = tarifa8hInvitado;
    }

    public ImpuestodFilter getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(ImpuestodFilter impuesto) {
        this.impuesto = impuesto;
    }

    public LongFilter getSedeId() {
        return sedeId;
    }

    public void setSedeId(LongFilter sedeId) {
        this.sedeId = sedeId;
    }

    public LongFilter getTipoEspacioId() {
        return tipoEspacioId;
    }

    public void setTipoEspacioId(LongFilter tipoEspacioId) {
        this.tipoEspacioId = tipoEspacioId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EspacioLibreCriteria that = (EspacioLibreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(capacidadInstalada, that.capacidadInstalada) &&
            Objects.equals(wifi, that.wifi) &&
            Objects.equals(tarifa1hMiembro, that.tarifa1hMiembro) &&
            Objects.equals(tarifa2hMiembro, that.tarifa2hMiembro) &&
            Objects.equals(tarifa3hMiembro, that.tarifa3hMiembro) &&
            Objects.equals(tarifa4hMiembro, that.tarifa4hMiembro) &&
            Objects.equals(tarifa5hMiembro, that.tarifa5hMiembro) &&
            Objects.equals(tarifa6hMiembro, that.tarifa6hMiembro) &&
            Objects.equals(tarifa7hMiembro, that.tarifa7hMiembro) &&
            Objects.equals(tarifa8hMiembro, that.tarifa8hMiembro) &&
            Objects.equals(tarifa1hInvitado, that.tarifa1hInvitado) &&
            Objects.equals(tarifa2hInvitado, that.tarifa2hInvitado) &&
            Objects.equals(tarifa3hInvitado, that.tarifa3hInvitado) &&
            Objects.equals(tarifa4hInvitado, that.tarifa4hInvitado) &&
            Objects.equals(tarifa5hInvitado, that.tarifa5hInvitado) &&
            Objects.equals(tarifa6hInvitado, that.tarifa6hInvitado) &&
            Objects.equals(tarifa7hInvitado, that.tarifa7hInvitado) &&
            Objects.equals(tarifa8hInvitado, that.tarifa8hInvitado) &&
            Objects.equals(impuesto, that.impuesto) &&
            Objects.equals(sedeId, that.sedeId) &&
            Objects.equals(tipoEspacioId, that.tipoEspacioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        capacidadInstalada,
        wifi,
        tarifa1hMiembro,
        tarifa2hMiembro,
        tarifa3hMiembro,
        tarifa4hMiembro,
        tarifa5hMiembro,
        tarifa6hMiembro,
        tarifa7hMiembro,
        tarifa8hMiembro,
        tarifa1hInvitado,
        tarifa2hInvitado,
        tarifa3hInvitado,
        tarifa4hInvitado,
        tarifa5hInvitado,
        tarifa6hInvitado,
        tarifa7hInvitado,
        tarifa8hInvitado,
        impuesto,
        sedeId,
        tipoEspacioId
        );
    }

    @Override
    public String toString() {
        return "EspacioLibreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (capacidadInstalada != null ? "capacidadInstalada=" + capacidadInstalada + ", " : "") +
                (wifi != null ? "wifi=" + wifi + ", " : "") +
                (tarifa1hMiembro != null ? "tarifa1hMiembro=" + tarifa1hMiembro + ", " : "") +
                (tarifa2hMiembro != null ? "tarifa2hMiembro=" + tarifa2hMiembro + ", " : "") +
                (tarifa3hMiembro != null ? "tarifa3hMiembro=" + tarifa3hMiembro + ", " : "") +
                (tarifa4hMiembro != null ? "tarifa4hMiembro=" + tarifa4hMiembro + ", " : "") +
                (tarifa5hMiembro != null ? "tarifa5hMiembro=" + tarifa5hMiembro + ", " : "") +
                (tarifa6hMiembro != null ? "tarifa6hMiembro=" + tarifa6hMiembro + ", " : "") +
                (tarifa7hMiembro != null ? "tarifa7hMiembro=" + tarifa7hMiembro + ", " : "") +
                (tarifa8hMiembro != null ? "tarifa8hMiembro=" + tarifa8hMiembro + ", " : "") +
                (tarifa1hInvitado != null ? "tarifa1hInvitado=" + tarifa1hInvitado + ", " : "") +
                (tarifa2hInvitado != null ? "tarifa2hInvitado=" + tarifa2hInvitado + ", " : "") +
                (tarifa3hInvitado != null ? "tarifa3hInvitado=" + tarifa3hInvitado + ", " : "") +
                (tarifa4hInvitado != null ? "tarifa4hInvitado=" + tarifa4hInvitado + ", " : "") +
                (tarifa5hInvitado != null ? "tarifa5hInvitado=" + tarifa5hInvitado + ", " : "") +
                (tarifa6hInvitado != null ? "tarifa6hInvitado=" + tarifa6hInvitado + ", " : "") +
                (tarifa7hInvitado != null ? "tarifa7hInvitado=" + tarifa7hInvitado + ", " : "") +
                (tarifa8hInvitado != null ? "tarifa8hInvitado=" + tarifa8hInvitado + ", " : "") +
                (impuesto != null ? "impuesto=" + impuesto + ", " : "") +
                (sedeId != null ? "sedeId=" + sedeId + ", " : "") +
                (tipoEspacioId != null ? "tipoEspacioId=" + tipoEspacioId + ", " : "") +
            "}";
    }

}
