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
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.TipoEspacio} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.TipoEspacioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tipo-espacios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TipoEspacioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tipoEspacio;

    public TipoEspacioCriteria(){
    }

    public TipoEspacioCriteria(TipoEspacioCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tipoEspacio = other.tipoEspacio == null ? null : other.tipoEspacio.copy();
    }

    @Override
    public TipoEspacioCriteria copy() {
        return new TipoEspacioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTipoEspacio() {
        return tipoEspacio;
    }

    public void setTipoEspacio(StringFilter tipoEspacio) {
        this.tipoEspacio = tipoEspacio;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TipoEspacioCriteria that = (TipoEspacioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipoEspacio, that.tipoEspacio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipoEspacio
        );
    }

    @Override
    public String toString() {
        return "TipoEspacioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipoEspacio != null ? "tipoEspacio=" + tipoEspacio + ", " : "") +
            "}";
    }

}
