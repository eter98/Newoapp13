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

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Empresa} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.EmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter razonSocial;

    private StringFilter nit;

    private StringFilter direccion;

    private StringFilter telefono;

    private StringFilter correo;

    private StringFilter celular;

    private LongFilter aliadoId;

    public EmpresaCriteria(){
    }

    public EmpresaCriteria(EmpresaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.razonSocial = other.razonSocial == null ? null : other.razonSocial.copy();
        this.nit = other.nit == null ? null : other.nit.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.correo = other.correo == null ? null : other.correo.copy();
        this.celular = other.celular == null ? null : other.celular.copy();
        this.aliadoId = other.aliadoId == null ? null : other.aliadoId.copy();
    }

    @Override
    public EmpresaCriteria copy() {
        return new EmpresaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(StringFilter razonSocial) {
        this.razonSocial = razonSocial;
    }

    public StringFilter getNit() {
        return nit;
    }

    public void setNit(StringFilter nit) {
        this.nit = nit;
    }

    public StringFilter getDireccion() {
        return direccion;
    }

    public void setDireccion(StringFilter direccion) {
        this.direccion = direccion;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }

    public StringFilter getCorreo() {
        return correo;
    }

    public void setCorreo(StringFilter correo) {
        this.correo = correo;
    }

    public StringFilter getCelular() {
        return celular;
    }

    public void setCelular(StringFilter celular) {
        this.celular = celular;
    }

    public LongFilter getAliadoId() {
        return aliadoId;
    }

    public void setAliadoId(LongFilter aliadoId) {
        this.aliadoId = aliadoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmpresaCriteria that = (EmpresaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(razonSocial, that.razonSocial) &&
            Objects.equals(nit, that.nit) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(correo, that.correo) &&
            Objects.equals(celular, that.celular) &&
            Objects.equals(aliadoId, that.aliadoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        razonSocial,
        nit,
        direccion,
        telefono,
        correo,
        celular,
        aliadoId
        );
    }

    @Override
    public String toString() {
        return "EmpresaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (razonSocial != null ? "razonSocial=" + razonSocial + ", " : "") +
                (nit != null ? "nit=" + nit + ", " : "") +
                (direccion != null ? "direccion=" + direccion + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (correo != null ? "correo=" + correo + ", " : "") +
                (celular != null ? "celular=" + celular + ", " : "") +
                (aliadoId != null ? "aliadoId=" + aliadoId + ", " : "") +
            "}";
    }

}
