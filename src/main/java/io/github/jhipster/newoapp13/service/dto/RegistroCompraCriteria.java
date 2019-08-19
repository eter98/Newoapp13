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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.RegistroCompra} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.RegistroCompraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /registro-compras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RegistroCompraCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter valor;

    private InstantFilter fechaRegistro;

    private IntegerFilter idTransaccion;

    private LongFilter miembroId;

    private LongFilter tipoRegistroId;

    public RegistroCompraCriteria(){
    }

    public RegistroCompraCriteria(RegistroCompraCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.fechaRegistro = other.fechaRegistro == null ? null : other.fechaRegistro.copy();
        this.idTransaccion = other.idTransaccion == null ? null : other.idTransaccion.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
        this.tipoRegistroId = other.tipoRegistroId == null ? null : other.tipoRegistroId.copy();
    }

    @Override
    public RegistroCompraCriteria copy() {
        return new RegistroCompraCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getValor() {
        return valor;
    }

    public void setValor(IntegerFilter valor) {
        this.valor = valor;
    }

    public InstantFilter getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(InstantFilter fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public IntegerFilter getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(IntegerFilter idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public LongFilter getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(LongFilter miembroId) {
        this.miembroId = miembroId;
    }

    public LongFilter getTipoRegistroId() {
        return tipoRegistroId;
    }

    public void setTipoRegistroId(LongFilter tipoRegistroId) {
        this.tipoRegistroId = tipoRegistroId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RegistroCompraCriteria that = (RegistroCompraCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(fechaRegistro, that.fechaRegistro) &&
            Objects.equals(idTransaccion, that.idTransaccion) &&
            Objects.equals(miembroId, that.miembroId) &&
            Objects.equals(tipoRegistroId, that.tipoRegistroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        valor,
        fechaRegistro,
        idTransaccion,
        miembroId,
        tipoRegistroId
        );
    }

    @Override
    public String toString() {
        return "RegistroCompraCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (fechaRegistro != null ? "fechaRegistro=" + fechaRegistro + ", " : "") +
                (idTransaccion != null ? "idTransaccion=" + idTransaccion + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
                (tipoRegistroId != null ? "tipoRegistroId=" + tipoRegistroId + ", " : "") +
            "}";
    }

}
