package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.Beneficiosd;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Beneficio} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.BeneficioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /beneficios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BeneficioCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Beneficiosd
     */
    public static class BeneficiosdFilter extends Filter<Beneficiosd> {

        public BeneficiosdFilter() {
        }

        public BeneficiosdFilter(BeneficiosdFilter filter) {
            super(filter);
        }

        @Override
        public BeneficiosdFilter copy() {
            return new BeneficiosdFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BeneficiosdFilter tipoBeneficio;

    private IntegerFilter descuento;

    private LongFilter miembroId;

    public BeneficioCriteria(){
    }

    public BeneficioCriteria(BeneficioCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tipoBeneficio = other.tipoBeneficio == null ? null : other.tipoBeneficio.copy();
        this.descuento = other.descuento == null ? null : other.descuento.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
    }

    @Override
    public BeneficioCriteria copy() {
        return new BeneficioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BeneficiosdFilter getTipoBeneficio() {
        return tipoBeneficio;
    }

    public void setTipoBeneficio(BeneficiosdFilter tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
    }

    public IntegerFilter getDescuento() {
        return descuento;
    }

    public void setDescuento(IntegerFilter descuento) {
        this.descuento = descuento;
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
        final BeneficioCriteria that = (BeneficioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipoBeneficio, that.tipoBeneficio) &&
            Objects.equals(descuento, that.descuento) &&
            Objects.equals(miembroId, that.miembroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipoBeneficio,
        descuento,
        miembroId
        );
    }

    @Override
    public String toString() {
        return "BeneficioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipoBeneficio != null ? "tipoBeneficio=" + tipoBeneficio + ", " : "") +
                (descuento != null ? "descuento=" + descuento + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
            "}";
    }

}
