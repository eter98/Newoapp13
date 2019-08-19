package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.Categoriad;
import io.github.jhipster.newoapp13.domain.enumeration.EstadoPublicaciond;
import io.github.jhipster.newoapp13.domain.enumeration.TipoConsumod;
import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Blog} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.BlogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /blogs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BlogCriteria implements Serializable, Criteria {
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
    /**
     * Class for filtering EstadoPublicaciond
     */
    public static class EstadoPublicaciondFilter extends Filter<EstadoPublicaciond> {

        public EstadoPublicaciondFilter() {
        }

        public EstadoPublicaciondFilter(EstadoPublicaciondFilter filter) {
            super(filter);
        }

        @Override
        public EstadoPublicaciondFilter copy() {
            return new EstadoPublicaciondFilter(this);
        }

    }
    /**
     * Class for filtering TipoConsumod
     */
    public static class TipoConsumodFilter extends Filter<TipoConsumod> {

        public TipoConsumodFilter() {
        }

        public TipoConsumodFilter(TipoConsumodFilter filter) {
            super(filter);
        }

        @Override
        public TipoConsumodFilter copy() {
            return new TipoConsumodFilter(this);
        }

    }
    /**
     * Class for filtering Impuestod
     */
    public static class ImpuestodFilter extends Filter<Impuestod> {

        public ImpuestodFilter() {
        }

        public ImpuestodFilter(ImpuestodFilter filter) {
            super(filter);
        }

        @Override
        public ImpuestodFilter copy() {
            return new ImpuestodFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private StringFilter descripcion;

    private CategoriadFilter tipoContenido;

    private LocalDateFilter fecha;

    private EstadoPublicaciondFilter estadoPublicacion;

    private TipoConsumodFilter tipoConsumo;

    private FloatFilter valor;

    private ImpuestodFilter impuesto;

    private LongFilter miembroId;

    private LongFilter categoriaBlogId;

    private LongFilter gruposId;

    public BlogCriteria(){
    }

    public BlogCriteria(BlogCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.tipoContenido = other.tipoContenido == null ? null : other.tipoContenido.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.estadoPublicacion = other.estadoPublicacion == null ? null : other.estadoPublicacion.copy();
        this.tipoConsumo = other.tipoConsumo == null ? null : other.tipoConsumo.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.impuesto = other.impuesto == null ? null : other.impuesto.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
        this.categoriaBlogId = other.categoriaBlogId == null ? null : other.categoriaBlogId.copy();
        this.gruposId = other.gruposId == null ? null : other.gruposId.copy();
    }

    @Override
    public BlogCriteria copy() {
        return new BlogCriteria(this);
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

    public EstadoPublicaciondFilter getEstadoPublicacion() {
        return estadoPublicacion;
    }

    public void setEstadoPublicacion(EstadoPublicaciondFilter estadoPublicacion) {
        this.estadoPublicacion = estadoPublicacion;
    }

    public TipoConsumodFilter getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(TipoConsumodFilter tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public FloatFilter getValor() {
        return valor;
    }

    public void setValor(FloatFilter valor) {
        this.valor = valor;
    }

    public ImpuestodFilter getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(ImpuestodFilter impuesto) {
        this.impuesto = impuesto;
    }

    public LongFilter getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(LongFilter miembroId) {
        this.miembroId = miembroId;
    }

    public LongFilter getCategoriaBlogId() {
        return categoriaBlogId;
    }

    public void setCategoriaBlogId(LongFilter categoriaBlogId) {
        this.categoriaBlogId = categoriaBlogId;
    }

    public LongFilter getGruposId() {
        return gruposId;
    }

    public void setGruposId(LongFilter gruposId) {
        this.gruposId = gruposId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BlogCriteria that = (BlogCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(tipoContenido, that.tipoContenido) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(estadoPublicacion, that.estadoPublicacion) &&
            Objects.equals(tipoConsumo, that.tipoConsumo) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(impuesto, that.impuesto) &&
            Objects.equals(miembroId, that.miembroId) &&
            Objects.equals(categoriaBlogId, that.categoriaBlogId) &&
            Objects.equals(gruposId, that.gruposId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        titulo,
        descripcion,
        tipoContenido,
        fecha,
        estadoPublicacion,
        tipoConsumo,
        valor,
        impuesto,
        miembroId,
        categoriaBlogId,
        gruposId
        );
    }

    @Override
    public String toString() {
        return "BlogCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (tipoContenido != null ? "tipoContenido=" + tipoContenido + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (estadoPublicacion != null ? "estadoPublicacion=" + estadoPublicacion + ", " : "") +
                (tipoConsumo != null ? "tipoConsumo=" + tipoConsumo + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (impuesto != null ? "impuesto=" + impuesto + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
                (categoriaBlogId != null ? "categoriaBlogId=" + categoriaBlogId + ", " : "") +
                (gruposId != null ? "gruposId=" + gruposId + ", " : "") +
            "}";
    }

}
