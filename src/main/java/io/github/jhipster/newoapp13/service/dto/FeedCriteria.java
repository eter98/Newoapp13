package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.Categoriad;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Feed} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.FeedResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /feeds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FeedCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Categoriad
     */
    public static class CategoriadFilter extends Filter<Categoriad> {

        public CategoriadFilter() {
        }

        public CategoriadFilter(CategoriadFilter filter) {
            super(filter);
        }

        @Override
        public CategoriadFilter copy() {
            return new CategoriadFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private StringFilter descripcion;

    private CategoriadFilter tipoContenido;

    private LocalDateFilter fecha;

    private LongFilter miembroId;

    private LongFilter categoriaFeedId;

    public FeedCriteria(){
    }

    public FeedCriteria(FeedCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.tipoContenido = other.tipoContenido == null ? null : other.tipoContenido.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
        this.categoriaFeedId = other.categoriaFeedId == null ? null : other.categoriaFeedId.copy();
    }

    @Override
    public FeedCriteria copy() {
        return new FeedCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public CategoriadFilter getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(CategoriadFilter tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public LocalDateFilter getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateFilter fecha) {
        this.fecha = fecha;
    }

    public LongFilter getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(LongFilter miembroId) {
        this.miembroId = miembroId;
    }

    public LongFilter getCategoriaFeedId() {
        return categoriaFeedId;
    }

    public void setCategoriaFeedId(LongFilter categoriaFeedId) {
        this.categoriaFeedId = categoriaFeedId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FeedCriteria that = (FeedCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(tipoContenido, that.tipoContenido) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(miembroId, that.miembroId) &&
            Objects.equals(categoriaFeedId, that.categoriaFeedId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        titulo,
        descripcion,
        tipoContenido,
        fecha,
        miembroId,
        categoriaFeedId
        );
    }

    @Override
    public String toString() {
        return "FeedCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (tipoContenido != null ? "tipoContenido=" + tipoContenido + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
                (categoriaFeedId != null ? "categoriaFeedId=" + categoriaFeedId + ", " : "") +
            "}";
    }

}
