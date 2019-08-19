package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.TipoDocumentod;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Invitados} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.InvitadosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invitados?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InvitadosCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoDocumentod
     */
    public static class TipoDocumentodFilter extends Filter<TipoDocumentod> {

        public TipoDocumentodFilter() {
        }

        public TipoDocumentodFilter(TipoDocumentodFilter filter) {
            super(filter);
        }

        @Override
        public TipoDocumentodFilter copy() {
            return new TipoDocumentodFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter apellido;

    private TipoDocumentodFilter tipoDocumento;

    private StringFilter identificacion;

    private StringFilter correo;

    private StringFilter telefono;

    private LongFilter miembroId;

    public InvitadosCriteria(){
    }

    public InvitadosCriteria(InvitadosCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.apellido = other.apellido == null ? null : other.apellido.copy();
        this.tipoDocumento = other.tipoDocumento == null ? null : other.tipoDocumento.copy();
        this.identificacion = other.identificacion == null ? null : other.identificacion.copy();
        this.correo = other.correo == null ? null : other.correo.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
    }

    @Override
    public InvitadosCriteria copy() {
        return new InvitadosCriteria(this);
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

    public StringFilter getApellido() {
        return apellido;
    }

    public void setApellido(StringFilter apellido) {
        this.apellido = apellido;
    }

    public TipoDocumentodFilter getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentodFilter tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public StringFilter getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(StringFilter identificacion) {
        this.identificacion = identificacion;
    }

    public StringFilter getCorreo() {
        return correo;
    }

    public void setCorreo(StringFilter correo) {
        this.correo = correo;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
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
        final InvitadosCriteria that = (InvitadosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(apellido, that.apellido) &&
            Objects.equals(tipoDocumento, that.tipoDocumento) &&
            Objects.equals(identificacion, that.identificacion) &&
            Objects.equals(correo, that.correo) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(miembroId, that.miembroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        apellido,
        tipoDocumento,
        identificacion,
        correo,
        telefono,
        miembroId
        );
    }

    @Override
    public String toString() {
        return "InvitadosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (apellido != null ? "apellido=" + apellido + ", " : "") +
                (tipoDocumento != null ? "tipoDocumento=" + tipoDocumento + ", " : "") +
                (identificacion != null ? "identificacion=" + identificacion + ", " : "") +
                (correo != null ? "correo=" + correo + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
            "}";
    }

}
