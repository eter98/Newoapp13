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
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.RegistroCompra} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.RegistroCompraResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /registro-compras?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RegistroCompraCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter consumoMarket;

    private IntegerFilter valor;

    private LongFilter miembroId;

    public RegistroCompraCriteria(){
    }

    public RegistroCompraCriteria(RegistroCompraCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.consumoMarket = other.consumoMarket == null ? null : other.consumoMarket.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
    }

    @Override
    public RegistroCompraCriteria copy() {
        return new RegistroCompraCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getConsumoMarket() {
        return consumoMarket;
    }

    public void setConsumoMarket(BooleanFilter consumoMarket) {
        this.consumoMarket = consumoMarket;
    }

    public IntegerFilter getValor() {
        return valor;
    }

    public void setValor(IntegerFilter valor) {
        this.valor = valor;
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
        final RegistroCompraCriteria that = (RegistroCompraCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(consumoMarket, that.consumoMarket) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(miembroId, that.miembroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        consumoMarket,
        valor,
        miembroId
        );
    }

    @Override
    public String toString() {
        return "RegistroCompraCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (consumoMarket != null ? "consumoMarket=" + consumoMarket + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
            "}";
    }

}
