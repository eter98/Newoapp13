package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.TipoIniciod;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.UsoRecursoFisico} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.UsoRecursoFisicoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /uso-recurso-fisicos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UsoRecursoFisicoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoIniciod
     */
    public static class TipoIniciodFilter extends Filter<TipoIniciod> {

        public TipoIniciodFilter() {
        }

        public TipoIniciodFilter(TipoIniciodFilter filter) {
            super(filter);
        }

        @Override
        public TipoIniciodFilter copy() {
            return new TipoIniciodFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter registroFechaInicio;

    private TipoIniciodFilter tipoRegistro;

    private LongFilter recursoId;

    private LongFilter miembroId;

    public UsoRecursoFisicoCriteria(){
    }

    public UsoRecursoFisicoCriteria(UsoRecursoFisicoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.registroFechaInicio = other.registroFechaInicio == null ? null : other.registroFechaInicio.copy();
        this.tipoRegistro = other.tipoRegistro == null ? null : other.tipoRegistro.copy();
        this.recursoId = other.recursoId == null ? null : other.recursoId.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
    }

    @Override
    public UsoRecursoFisicoCriteria copy() {
        return new UsoRecursoFisicoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getRegistroFechaInicio() {
        return registroFechaInicio;
    }

    public void setRegistroFechaInicio(InstantFilter registroFechaInicio) {
        this.registroFechaInicio = registroFechaInicio;
    }

    public TipoIniciodFilter getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(TipoIniciodFilter tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public LongFilter getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(LongFilter recursoId) {
        this.recursoId = recursoId;
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
        final UsoRecursoFisicoCriteria that = (UsoRecursoFisicoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(registroFechaInicio, that.registroFechaInicio) &&
            Objects.equals(tipoRegistro, that.tipoRegistro) &&
            Objects.equals(recursoId, that.recursoId) &&
            Objects.equals(miembroId, that.miembroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        registroFechaInicio,
        tipoRegistro,
        recursoId,
        miembroId
        );
    }

    @Override
    public String toString() {
        return "UsoRecursoFisicoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (registroFechaInicio != null ? "registroFechaInicio=" + registroFechaInicio + ", " : "") +
                (tipoRegistro != null ? "tipoRegistro=" + tipoRegistro + ", " : "") +
                (recursoId != null ? "recursoId=" + recursoId + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
            "}";
    }

}
