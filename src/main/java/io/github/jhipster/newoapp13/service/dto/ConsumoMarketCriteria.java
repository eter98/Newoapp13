package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
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
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.ConsumoMarket} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.ConsumoMarketResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /consumo-markets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConsumoMarketCriteria implements Serializable, Criteria {
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

    private IntegerFilter totalConsumido;

    private LocalDateFilter registroFechaInicial;

    private LocalDateFilter registroFechaFinal;

    private ImpuestodFilter impuesto;

    private LongFilter miembroId;

    public ConsumoMarketCriteria(){
    }

    public ConsumoMarketCriteria(ConsumoMarketCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.totalConsumido = other.totalConsumido == null ? null : other.totalConsumido.copy();
        this.registroFechaInicial = other.registroFechaInicial == null ? null : other.registroFechaInicial.copy();
        this.registroFechaFinal = other.registroFechaFinal == null ? null : other.registroFechaFinal.copy();
        this.impuesto = other.impuesto == null ? null : other.impuesto.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
    }

    @Override
    public ConsumoMarketCriteria copy() {
        return new ConsumoMarketCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getTotalConsumido() {
        return totalConsumido;
    }

    public void setTotalConsumido(IntegerFilter totalConsumido) {
        this.totalConsumido = totalConsumido;
    }

    public LocalDateFilter getRegistroFechaInicial() {
        return registroFechaInicial;
    }

    public void setRegistroFechaInicial(LocalDateFilter registroFechaInicial) {
        this.registroFechaInicial = registroFechaInicial;
    }

    public LocalDateFilter getRegistroFechaFinal() {
        return registroFechaFinal;
    }

    public void setRegistroFechaFinal(LocalDateFilter registroFechaFinal) {
        this.registroFechaFinal = registroFechaFinal;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ConsumoMarketCriteria that = (ConsumoMarketCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(totalConsumido, that.totalConsumido) &&
            Objects.equals(registroFechaInicial, that.registroFechaInicial) &&
            Objects.equals(registroFechaFinal, that.registroFechaFinal) &&
            Objects.equals(impuesto, that.impuesto) &&
            Objects.equals(miembroId, that.miembroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        totalConsumido,
        registroFechaInicial,
        registroFechaFinal,
        impuesto,
        miembroId
        );
    }

    @Override
    public String toString() {
        return "ConsumoMarketCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (totalConsumido != null ? "totalConsumido=" + totalConsumido + ", " : "") +
                (registroFechaInicial != null ? "registroFechaInicial=" + registroFechaInicial + ", " : "") +
                (registroFechaFinal != null ? "registroFechaFinal=" + registroFechaFinal + ", " : "") +
                (impuesto != null ? "impuesto=" + impuesto + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
            "}";
    }

}
