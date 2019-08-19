package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.TipoRecursod;
import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.RecursosFisicos} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.RecursosFisicosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /recursos-fisicos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RecursosFisicosCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoRecursod
     */
    public static class TipoRecursodFilter extends Filter<TipoRecursod> {

        public TipoRecursodFilter() {
        }

        public TipoRecursodFilter(TipoRecursodFilter filter) {
            super(filter);
        }

        @Override
        public TipoRecursodFilter copy() {
            return new TipoRecursodFilter(this);
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

    private StringFilter recurso;

    private TipoRecursodFilter tipo;

    private StringFilter unidadMedida;

    private IntegerFilter valorUnitario;

    private IntegerFilter valor1H;

    private IntegerFilter valor2H;

    private IntegerFilter valor3H;

    private IntegerFilter valor4H;

    private IntegerFilter valor5H;

    private IntegerFilter valor6H;

    private IntegerFilter valor7H;

    private IntegerFilter valor8H;

    private IntegerFilter valor9H;

    private IntegerFilter valor10H;

    private IntegerFilter valor11H;

    private IntegerFilter valor12H;

    private ImpuestodFilter impuesto;

    private LongFilter sedeId;

    private LongFilter tipoRecursoId;

    public RecursosFisicosCriteria(){
    }

    public RecursosFisicosCriteria(RecursosFisicosCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.recurso = other.recurso == null ? null : other.recurso.copy();
        this.tipo = other.tipo == null ? null : other.tipo.copy();
        this.unidadMedida = other.unidadMedida == null ? null : other.unidadMedida.copy();
        this.valorUnitario = other.valorUnitario == null ? null : other.valorUnitario.copy();
        this.valor1H = other.valor1H == null ? null : other.valor1H.copy();
        this.valor2H = other.valor2H == null ? null : other.valor2H.copy();
        this.valor3H = other.valor3H == null ? null : other.valor3H.copy();
        this.valor4H = other.valor4H == null ? null : other.valor4H.copy();
        this.valor5H = other.valor5H == null ? null : other.valor5H.copy();
        this.valor6H = other.valor6H == null ? null : other.valor6H.copy();
        this.valor7H = other.valor7H == null ? null : other.valor7H.copy();
        this.valor8H = other.valor8H == null ? null : other.valor8H.copy();
        this.valor9H = other.valor9H == null ? null : other.valor9H.copy();
        this.valor10H = other.valor10H == null ? null : other.valor10H.copy();
        this.valor11H = other.valor11H == null ? null : other.valor11H.copy();
        this.valor12H = other.valor12H == null ? null : other.valor12H.copy();
        this.impuesto = other.impuesto == null ? null : other.impuesto.copy();
        this.sedeId = other.sedeId == null ? null : other.sedeId.copy();
        this.tipoRecursoId = other.tipoRecursoId == null ? null : other.tipoRecursoId.copy();
    }

    @Override
    public RecursosFisicosCriteria copy() {
        return new RecursosFisicosCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRecurso() {
        return recurso;
    }

    public void setRecurso(StringFilter recurso) {
        this.recurso = recurso;
    }

    public TipoRecursodFilter getTipo() {
        return tipo;
    }

    public void setTipo(TipoRecursodFilter tipo) {
        this.tipo = tipo;
    }

    public StringFilter getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(StringFilter unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public IntegerFilter getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(IntegerFilter valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public IntegerFilter getValor1H() {
        return valor1H;
    }

    public void setValor1H(IntegerFilter valor1H) {
        this.valor1H = valor1H;
    }

    public IntegerFilter getValor2H() {
        return valor2H;
    }

    public void setValor2H(IntegerFilter valor2H) {
        this.valor2H = valor2H;
    }

    public IntegerFilter getValor3H() {
        return valor3H;
    }

    public void setValor3H(IntegerFilter valor3H) {
        this.valor3H = valor3H;
    }

    public IntegerFilter getValor4H() {
        return valor4H;
    }

    public void setValor4H(IntegerFilter valor4H) {
        this.valor4H = valor4H;
    }

    public IntegerFilter getValor5H() {
        return valor5H;
    }

    public void setValor5H(IntegerFilter valor5H) {
        this.valor5H = valor5H;
    }

    public IntegerFilter getValor6H() {
        return valor6H;
    }

    public void setValor6H(IntegerFilter valor6H) {
        this.valor6H = valor6H;
    }

    public IntegerFilter getValor7H() {
        return valor7H;
    }

    public void setValor7H(IntegerFilter valor7H) {
        this.valor7H = valor7H;
    }

    public IntegerFilter getValor8H() {
        return valor8H;
    }

    public void setValor8H(IntegerFilter valor8H) {
        this.valor8H = valor8H;
    }

    public IntegerFilter getValor9H() {
        return valor9H;
    }

    public void setValor9H(IntegerFilter valor9H) {
        this.valor9H = valor9H;
    }

    public IntegerFilter getValor10H() {
        return valor10H;
    }

    public void setValor10H(IntegerFilter valor10H) {
        this.valor10H = valor10H;
    }

    public IntegerFilter getValor11H() {
        return valor11H;
    }

    public void setValor11H(IntegerFilter valor11H) {
        this.valor11H = valor11H;
    }

    public IntegerFilter getValor12H() {
        return valor12H;
    }

    public void setValor12H(IntegerFilter valor12H) {
        this.valor12H = valor12H;
    }

    public ImpuestodFilter getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(ImpuestodFilter impuesto) {
        this.impuesto = impuesto;
    }

    public LongFilter getSedeId() {
        return sedeId;
    }

    public void setSedeId(LongFilter sedeId) {
        this.sedeId = sedeId;
    }

    public LongFilter getTipoRecursoId() {
        return tipoRecursoId;
    }

    public void setTipoRecursoId(LongFilter tipoRecursoId) {
        this.tipoRecursoId = tipoRecursoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RecursosFisicosCriteria that = (RecursosFisicosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(recurso, that.recurso) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(unidadMedida, that.unidadMedida) &&
            Objects.equals(valorUnitario, that.valorUnitario) &&
            Objects.equals(valor1H, that.valor1H) &&
            Objects.equals(valor2H, that.valor2H) &&
            Objects.equals(valor3H, that.valor3H) &&
            Objects.equals(valor4H, that.valor4H) &&
            Objects.equals(valor5H, that.valor5H) &&
            Objects.equals(valor6H, that.valor6H) &&
            Objects.equals(valor7H, that.valor7H) &&
            Objects.equals(valor8H, that.valor8H) &&
            Objects.equals(valor9H, that.valor9H) &&
            Objects.equals(valor10H, that.valor10H) &&
            Objects.equals(valor11H, that.valor11H) &&
            Objects.equals(valor12H, that.valor12H) &&
            Objects.equals(impuesto, that.impuesto) &&
            Objects.equals(sedeId, that.sedeId) &&
            Objects.equals(tipoRecursoId, that.tipoRecursoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        recurso,
        tipo,
        unidadMedida,
        valorUnitario,
        valor1H,
        valor2H,
        valor3H,
        valor4H,
        valor5H,
        valor6H,
        valor7H,
        valor8H,
        valor9H,
        valor10H,
        valor11H,
        valor12H,
        impuesto,
        sedeId,
        tipoRecursoId
        );
    }

    @Override
    public String toString() {
        return "RecursosFisicosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (recurso != null ? "recurso=" + recurso + ", " : "") +
                (tipo != null ? "tipo=" + tipo + ", " : "") +
                (unidadMedida != null ? "unidadMedida=" + unidadMedida + ", " : "") +
                (valorUnitario != null ? "valorUnitario=" + valorUnitario + ", " : "") +
                (valor1H != null ? "valor1H=" + valor1H + ", " : "") +
                (valor2H != null ? "valor2H=" + valor2H + ", " : "") +
                (valor3H != null ? "valor3H=" + valor3H + ", " : "") +
                (valor4H != null ? "valor4H=" + valor4H + ", " : "") +
                (valor5H != null ? "valor5H=" + valor5H + ", " : "") +
                (valor6H != null ? "valor6H=" + valor6H + ", " : "") +
                (valor7H != null ? "valor7H=" + valor7H + ", " : "") +
                (valor8H != null ? "valor8H=" + valor8H + ", " : "") +
                (valor9H != null ? "valor9H=" + valor9H + ", " : "") +
                (valor10H != null ? "valor10H=" + valor10H + ", " : "") +
                (valor11H != null ? "valor11H=" + valor11H + ", " : "") +
                (valor12H != null ? "valor12H=" + valor12H + ", " : "") +
                (impuesto != null ? "impuesto=" + impuesto + ", " : "") +
                (sedeId != null ? "sedeId=" + sedeId + ", " : "") +
                (tipoRecursoId != null ? "tipoRecursoId=" + tipoRecursoId + ", " : "") +
            "}";
    }

}
