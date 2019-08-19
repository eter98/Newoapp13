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
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Landing} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.LandingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /landings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LandingCriteria implements Serializable, Criteria {
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

    private StringFilter descripcion;

    private StringFilter telefonoNegocio;

    private IntegerFilter numeroPuestos;

    private IntegerFilter tarifa;

    private ImpuestodFilter impuesto;

    private LongFilter sedeId;

    public LandingCriteria(){
    }

    public LandingCriteria(LandingCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.telefonoNegocio = other.telefonoNegocio == null ? null : other.telefonoNegocio.copy();
        this.numeroPuestos = other.numeroPuestos == null ? null : other.numeroPuestos.copy();
        this.tarifa = other.tarifa == null ? null : other.tarifa.copy();
        this.impuesto = other.impuesto == null ? null : other.impuesto.copy();
        this.sedeId = other.sedeId == null ? null : other.sedeId.copy();
    }

    @Override
    public LandingCriteria copy() {
        return new LandingCriteria(this);
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

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public StringFilter getTelefonoNegocio() {
        return telefonoNegocio;
    }

    public void setTelefonoNegocio(StringFilter telefonoNegocio) {
        this.telefonoNegocio = telefonoNegocio;
    }

    public IntegerFilter getNumeroPuestos() {
        return numeroPuestos;
    }

    public void setNumeroPuestos(IntegerFilter numeroPuestos) {
        this.numeroPuestos = numeroPuestos;
    }

    public IntegerFilter getTarifa() {
        return tarifa;
    }

    public void setTarifa(IntegerFilter tarifa) {
        this.tarifa = tarifa;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LandingCriteria that = (LandingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(telefonoNegocio, that.telefonoNegocio) &&
            Objects.equals(numeroPuestos, that.numeroPuestos) &&
            Objects.equals(tarifa, that.tarifa) &&
            Objects.equals(impuesto, that.impuesto) &&
            Objects.equals(sedeId, that.sedeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        descripcion,
        telefonoNegocio,
        numeroPuestos,
        tarifa,
        impuesto,
        sedeId
        );
    }

    @Override
    public String toString() {
        return "LandingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (telefonoNegocio != null ? "telefonoNegocio=" + telefonoNegocio + ", " : "") +
                (numeroPuestos != null ? "numeroPuestos=" + numeroPuestos + ", " : "") +
                (tarifa != null ? "tarifa=" + tarifa + ", " : "") +
                (impuesto != null ? "impuesto=" + impuesto + ", " : "") +
                (sedeId != null ? "sedeId=" + sedeId + ", " : "") +
            "}";
    }

}
