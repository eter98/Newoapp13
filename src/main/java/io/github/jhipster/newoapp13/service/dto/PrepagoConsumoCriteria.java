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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.PrepagoConsumo} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.PrepagoConsumoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /prepago-consumos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PrepagoConsumoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter aporte;

    private IntegerFilter saldoActual;

    private LocalDateFilter fechaRegistro;

    private LocalDateFilter fechaSaldoActual;

    private LongFilter miembroId;

    private LongFilter tipoPrepagoId;

    public PrepagoConsumoCriteria(){
    }

    public PrepagoConsumoCriteria(PrepagoConsumoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.aporte = other.aporte == null ? null : other.aporte.copy();
        this.saldoActual = other.saldoActual == null ? null : other.saldoActual.copy();
        this.fechaRegistro = other.fechaRegistro == null ? null : other.fechaRegistro.copy();
        this.fechaSaldoActual = other.fechaSaldoActual == null ? null : other.fechaSaldoActual.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
        this.tipoPrepagoId = other.tipoPrepagoId == null ? null : other.tipoPrepagoId.copy();
    }

    @Override
    public PrepagoConsumoCriteria copy() {
        return new PrepagoConsumoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getAporte() {
        return aporte;
    }

    public void setAporte(IntegerFilter aporte) {
        this.aporte = aporte;
    }

    public IntegerFilter getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(IntegerFilter saldoActual) {
        this.saldoActual = saldoActual;
    }

    public LocalDateFilter getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateFilter fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateFilter getFechaSaldoActual() {
        return fechaSaldoActual;
    }

    public void setFechaSaldoActual(LocalDateFilter fechaSaldoActual) {
        this.fechaSaldoActual = fechaSaldoActual;
    }

    public LongFilter getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(LongFilter miembroId) {
        this.miembroId = miembroId;
    }

    public LongFilter getTipoPrepagoId() {
        return tipoPrepagoId;
    }

    public void setTipoPrepagoId(LongFilter tipoPrepagoId) {
        this.tipoPrepagoId = tipoPrepagoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PrepagoConsumoCriteria that = (PrepagoConsumoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(aporte, that.aporte) &&
            Objects.equals(saldoActual, that.saldoActual) &&
            Objects.equals(fechaRegistro, that.fechaRegistro) &&
            Objects.equals(fechaSaldoActual, that.fechaSaldoActual) &&
            Objects.equals(miembroId, that.miembroId) &&
            Objects.equals(tipoPrepagoId, that.tipoPrepagoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        aporte,
        saldoActual,
        fechaRegistro,
        fechaSaldoActual,
        miembroId,
        tipoPrepagoId
        );
    }

    @Override
    public String toString() {
        return "PrepagoConsumoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (aporte != null ? "aporte=" + aporte + ", " : "") +
                (saldoActual != null ? "saldoActual=" + saldoActual + ", " : "") +
                (fechaRegistro != null ? "fechaRegistro=" + fechaRegistro + ", " : "") +
                (fechaSaldoActual != null ? "fechaSaldoActual=" + fechaSaldoActual + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
                (tipoPrepagoId != null ? "tipoPrepagoId=" + tipoPrepagoId + ", " : "") +
            "}";
    }

}
