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
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.TipoPrepagoConsumo} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.TipoPrepagoConsumoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tipo-prepago-consumos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TipoPrepagoConsumoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter descripcion;

    private IntegerFilter valorMinimo;

    private IntegerFilter valorMaximo;

    private LongFilter tipoBeneficioId;

    public TipoPrepagoConsumoCriteria(){
    }

    public TipoPrepagoConsumoCriteria(TipoPrepagoConsumoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.valorMinimo = other.valorMinimo == null ? null : other.valorMinimo.copy();
        this.valorMaximo = other.valorMaximo == null ? null : other.valorMaximo.copy();
        this.tipoBeneficioId = other.tipoBeneficioId == null ? null : other.tipoBeneficioId.copy();
    }

    @Override
    public TipoPrepagoConsumoCriteria copy() {
        return new TipoPrepagoConsumoCriteria(this);
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

    public IntegerFilter getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(IntegerFilter valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public IntegerFilter getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(IntegerFilter valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public LongFilter getTipoBeneficioId() {
        return tipoBeneficioId;
    }

    public void setTipoBeneficioId(LongFilter tipoBeneficioId) {
        this.tipoBeneficioId = tipoBeneficioId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TipoPrepagoConsumoCriteria that = (TipoPrepagoConsumoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(valorMinimo, that.valorMinimo) &&
            Objects.equals(valorMaximo, that.valorMaximo) &&
            Objects.equals(tipoBeneficioId, that.tipoBeneficioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        descripcion,
        valorMinimo,
        valorMaximo,
        tipoBeneficioId
        );
    }

    @Override
    public String toString() {
        return "TipoPrepagoConsumoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (valorMinimo != null ? "valorMinimo=" + valorMinimo + ", " : "") +
                (valorMaximo != null ? "valorMaximo=" + valorMaximo + ", " : "") +
                (tipoBeneficioId != null ? "tipoBeneficioId=" + tipoBeneficioId + ", " : "") +
            "}";
    }

}
