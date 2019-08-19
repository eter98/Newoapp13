package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.TipoConsumod;
import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;
import io.github.jhipster.newoapp13.domain.enumeration.Categoriad;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Evento} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.EventoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /eventos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EventoCriteria implements Serializable, Criteria {
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

    private StringFilter nombreEvento;

    private StringFilter descripcion;

    private TipoConsumodFilter tipoConsumo;

    private FloatFilter valor;

    private ImpuestodFilter impuesto;

    private CategoriadFilter tipoEvento;

    private BooleanFilter eventoNEWO;

    private LongFilter categoriaEventoId;

    private LongFilter gruposId;

    private LongFilter miembroId;

    public EventoCriteria(){
    }

    public EventoCriteria(EventoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombreEvento = other.nombreEvento == null ? null : other.nombreEvento.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.tipoConsumo = other.tipoConsumo == null ? null : other.tipoConsumo.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.impuesto = other.impuesto == null ? null : other.impuesto.copy();
        this.tipoEvento = other.tipoEvento == null ? null : other.tipoEvento.copy();
        this.eventoNEWO = other.eventoNEWO == null ? null : other.eventoNEWO.copy();
        this.categoriaEventoId = other.categoriaEventoId == null ? null : other.categoriaEventoId.copy();
        this.gruposId = other.gruposId == null ? null : other.gruposId.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
    }

    @Override
    public EventoCriteria copy() {
        return new EventoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(StringFilter nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
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

    public CategoriadFilter getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(CategoriadFilter tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public BooleanFilter getEventoNEWO() {
        return eventoNEWO;
    }

    public void setEventoNEWO(BooleanFilter eventoNEWO) {
        this.eventoNEWO = eventoNEWO;
    }

    public LongFilter getCategoriaEventoId() {
        return categoriaEventoId;
    }

    public void setCategoriaEventoId(LongFilter categoriaEventoId) {
        this.categoriaEventoId = categoriaEventoId;
    }

    public LongFilter getGruposId() {
        return gruposId;
    }

    public void setGruposId(LongFilter gruposId) {
        this.gruposId = gruposId;
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
        final EventoCriteria that = (EventoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombreEvento, that.nombreEvento) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(tipoConsumo, that.tipoConsumo) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(impuesto, that.impuesto) &&
            Objects.equals(tipoEvento, that.tipoEvento) &&
            Objects.equals(eventoNEWO, that.eventoNEWO) &&
            Objects.equals(categoriaEventoId, that.categoriaEventoId) &&
            Objects.equals(gruposId, that.gruposId) &&
            Objects.equals(miembroId, that.miembroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombreEvento,
        descripcion,
        tipoConsumo,
        valor,
        impuesto,
        tipoEvento,
        eventoNEWO,
        categoriaEventoId,
        gruposId,
        miembroId
        );
    }

    @Override
    public String toString() {
        return "EventoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombreEvento != null ? "nombreEvento=" + nombreEvento + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (tipoConsumo != null ? "tipoConsumo=" + tipoConsumo + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (impuesto != null ? "impuesto=" + impuesto + ", " : "") +
                (tipoEvento != null ? "tipoEvento=" + tipoEvento + ", " : "") +
                (eventoNEWO != null ? "eventoNEWO=" + eventoNEWO + ", " : "") +
                (categoriaEventoId != null ? "categoriaEventoId=" + categoriaEventoId + ", " : "") +
                (gruposId != null ? "gruposId=" + gruposId + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
            "}";
    }

}
