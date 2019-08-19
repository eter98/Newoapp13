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
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.CategoriaContenidos} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.CategoriaContenidosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categoria-contenidos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoriaContenidosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter categoria;

    public CategoriaContenidosCriteria(){
    }

    public CategoriaContenidosCriteria(CategoriaContenidosCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.categoria = other.categoria == null ? null : other.categoria.copy();
    }

    @Override
    public CategoriaContenidosCriteria copy() {
        return new CategoriaContenidosCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCategoria() {
        return categoria;
    }

    public void setCategoria(StringFilter categoria) {
        this.categoria = categoria;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoriaContenidosCriteria that = (CategoriaContenidosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(categoria, that.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        categoria
        );
    }

    @Override
    public String toString() {
        return "CategoriaContenidosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (categoria != null ? "categoria=" + categoria + ", " : "") +
            "}";
    }

}
