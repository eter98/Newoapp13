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
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.MargenNewoProductos} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.MargenNewoProductosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /margen-newo-productos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MargenNewoProductosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter porcentajeMargen;

    private LongFilter productoId;

    public MargenNewoProductosCriteria(){
    }

    public MargenNewoProductosCriteria(MargenNewoProductosCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.porcentajeMargen = other.porcentajeMargen == null ? null : other.porcentajeMargen.copy();
        this.productoId = other.productoId == null ? null : other.productoId.copy();
    }

    @Override
    public MargenNewoProductosCriteria copy() {
        return new MargenNewoProductosCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getPorcentajeMargen() {
        return porcentajeMargen;
    }

    public void setPorcentajeMargen(IntegerFilter porcentajeMargen) {
        this.porcentajeMargen = porcentajeMargen;
    }

    public LongFilter getProductoId() {
        return productoId;
    }

    public void setProductoId(LongFilter productoId) {
        this.productoId = productoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MargenNewoProductosCriteria that = (MargenNewoProductosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(porcentajeMargen, that.porcentajeMargen) &&
            Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        porcentajeMargen,
        productoId
        );
    }

    @Override
    public String toString() {
        return "MargenNewoProductosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (porcentajeMargen != null ? "porcentajeMargen=" + porcentajeMargen + ", " : "") +
                (productoId != null ? "productoId=" + productoId + ", " : "") +
            "}";
    }

}
