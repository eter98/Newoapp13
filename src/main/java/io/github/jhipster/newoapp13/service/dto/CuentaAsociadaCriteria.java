package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.CuentaAsociada} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.CuentaAsociadaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cuenta-asociadas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CuentaAsociadaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter identificaciontitular;

    private StringFilter nombreTitular;

    private StringFilter apellidoTitular;

    private StringFilter numeroCuenta;

    private StringFilter tipoCuenta;

    private StringFilter codigoSeguridad;

    private LocalDateFilter fechaVencimiento;

    public CuentaAsociadaCriteria(){
    }

    public CuentaAsociadaCriteria(CuentaAsociadaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.identificaciontitular = other.identificaciontitular == null ? null : other.identificaciontitular.copy();
        this.nombreTitular = other.nombreTitular == null ? null : other.nombreTitular.copy();
        this.apellidoTitular = other.apellidoTitular == null ? null : other.apellidoTitular.copy();
        this.numeroCuenta = other.numeroCuenta == null ? null : other.numeroCuenta.copy();
        this.tipoCuenta = other.tipoCuenta == null ? null : other.tipoCuenta.copy();
        this.codigoSeguridad = other.codigoSeguridad == null ? null : other.codigoSeguridad.copy();
        this.fechaVencimiento = other.fechaVencimiento == null ? null : other.fechaVencimiento.copy();
    }

    @Override
    public CuentaAsociadaCriteria copy() {
        return new CuentaAsociadaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIdentificaciontitular() {
        return identificaciontitular;
    }

    public void setIdentificaciontitular(StringFilter identificaciontitular) {
        this.identificaciontitular = identificaciontitular;
    }

    public StringFilter getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(StringFilter nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public StringFilter getApellidoTitular() {
        return apellidoTitular;
    }

    public void setApellidoTitular(StringFilter apellidoTitular) {
        this.apellidoTitular = apellidoTitular;
    }

    public StringFilter getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(StringFilter numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public StringFilter getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(StringFilter tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public StringFilter getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(StringFilter codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public LocalDateFilter getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateFilter fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CuentaAsociadaCriteria that = (CuentaAsociadaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(identificaciontitular, that.identificaciontitular) &&
            Objects.equals(nombreTitular, that.nombreTitular) &&
            Objects.equals(apellidoTitular, that.apellidoTitular) &&
            Objects.equals(numeroCuenta, that.numeroCuenta) &&
            Objects.equals(tipoCuenta, that.tipoCuenta) &&
            Objects.equals(codigoSeguridad, that.codigoSeguridad) &&
            Objects.equals(fechaVencimiento, that.fechaVencimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        identificaciontitular,
        nombreTitular,
        apellidoTitular,
        numeroCuenta,
        tipoCuenta,
        codigoSeguridad,
        fechaVencimiento
        );
    }

    @Override
    public String toString() {
        return "CuentaAsociadaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (identificaciontitular != null ? "identificaciontitular=" + identificaciontitular + ", " : "") +
                (nombreTitular != null ? "nombreTitular=" + nombreTitular + ", " : "") +
                (apellidoTitular != null ? "apellidoTitular=" + apellidoTitular + ", " : "") +
                (numeroCuenta != null ? "numeroCuenta=" + numeroCuenta + ", " : "") +
                (tipoCuenta != null ? "tipoCuenta=" + tipoCuenta + ", " : "") +
                (codigoSeguridad != null ? "codigoSeguridad=" + codigoSeguridad + ", " : "") +
                (fechaVencimiento != null ? "fechaVencimiento=" + fechaVencimiento + ", " : "") +
            "}";
    }

}
