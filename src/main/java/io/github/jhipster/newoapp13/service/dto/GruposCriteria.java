package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.TipoGrupod;
import io.github.jhipster.newoapp13.domain.enumeration.TipoConsumod;
import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Grupos} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.GruposResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /grupos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GruposCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoGrupod
     */
    public static class TipoGrupodFilter extends Filter<TipoGrupod> {

        public TipoGrupodFilter() {
        }

        public TipoGrupodFilter(TipoGrupodFilter filter) {
            super(filter);
        }

        @Override
        public TipoGrupodFilter copy() {
            return new TipoGrupodFilter(this);
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

    private StringFilter nombreGrupo;

    private StringFilter instagram;

    private StringFilter facebook;

    private StringFilter twiter;

    private StringFilter linkedIn;

    private TipoGrupodFilter tipoGrupo;

    private TipoConsumodFilter tipoConsumo;

    private IntegerFilter valorSuscripcion;

    private ImpuestodFilter impuesto;

    private LongFilter categoriaGrupoId;

    public GruposCriteria(){
    }

    public GruposCriteria(GruposCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombreGrupo = other.nombreGrupo == null ? null : other.nombreGrupo.copy();
        this.instagram = other.instagram == null ? null : other.instagram.copy();
        this.facebook = other.facebook == null ? null : other.facebook.copy();
        this.twiter = other.twiter == null ? null : other.twiter.copy();
        this.linkedIn = other.linkedIn == null ? null : other.linkedIn.copy();
        this.tipoGrupo = other.tipoGrupo == null ? null : other.tipoGrupo.copy();
        this.tipoConsumo = other.tipoConsumo == null ? null : other.tipoConsumo.copy();
        this.valorSuscripcion = other.valorSuscripcion == null ? null : other.valorSuscripcion.copy();
        this.impuesto = other.impuesto == null ? null : other.impuesto.copy();
        this.categoriaGrupoId = other.categoriaGrupoId == null ? null : other.categoriaGrupoId.copy();
    }

    @Override
    public GruposCriteria copy() {
        return new GruposCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(StringFilter nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public StringFilter getInstagram() {
        return instagram;
    }

    public void setInstagram(StringFilter instagram) {
        this.instagram = instagram;
    }

    public StringFilter getFacebook() {
        return facebook;
    }

    public void setFacebook(StringFilter facebook) {
        this.facebook = facebook;
    }

    public StringFilter getTwiter() {
        return twiter;
    }

    public void setTwiter(StringFilter twiter) {
        this.twiter = twiter;
    }

    public StringFilter getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(StringFilter linkedIn) {
        this.linkedIn = linkedIn;
    }

    public TipoGrupodFilter getTipoGrupo() {
        return tipoGrupo;
    }

    public void setTipoGrupo(TipoGrupodFilter tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }

    public TipoConsumodFilter getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(TipoConsumodFilter tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public IntegerFilter getValorSuscripcion() {
        return valorSuscripcion;
    }

    public void setValorSuscripcion(IntegerFilter valorSuscripcion) {
        this.valorSuscripcion = valorSuscripcion;
    }

    public ImpuestodFilter getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(ImpuestodFilter impuesto) {
        this.impuesto = impuesto;
    }

    public LongFilter getCategoriaGrupoId() {
        return categoriaGrupoId;
    }

    public void setCategoriaGrupoId(LongFilter categoriaGrupoId) {
        this.categoriaGrupoId = categoriaGrupoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GruposCriteria that = (GruposCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombreGrupo, that.nombreGrupo) &&
            Objects.equals(instagram, that.instagram) &&
            Objects.equals(facebook, that.facebook) &&
            Objects.equals(twiter, that.twiter) &&
            Objects.equals(linkedIn, that.linkedIn) &&
            Objects.equals(tipoGrupo, that.tipoGrupo) &&
            Objects.equals(tipoConsumo, that.tipoConsumo) &&
            Objects.equals(valorSuscripcion, that.valorSuscripcion) &&
            Objects.equals(impuesto, that.impuesto) &&
            Objects.equals(categoriaGrupoId, that.categoriaGrupoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombreGrupo,
        instagram,
        facebook,
        twiter,
        linkedIn,
        tipoGrupo,
        tipoConsumo,
        valorSuscripcion,
        impuesto,
        categoriaGrupoId
        );
    }

    @Override
    public String toString() {
        return "GruposCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombreGrupo != null ? "nombreGrupo=" + nombreGrupo + ", " : "") +
                (instagram != null ? "instagram=" + instagram + ", " : "") +
                (facebook != null ? "facebook=" + facebook + ", " : "") +
                (twiter != null ? "twiter=" + twiter + ", " : "") +
                (linkedIn != null ? "linkedIn=" + linkedIn + ", " : "") +
                (tipoGrupo != null ? "tipoGrupo=" + tipoGrupo + ", " : "") +
                (tipoConsumo != null ? "tipoConsumo=" + tipoConsumo + ", " : "") +
                (valorSuscripcion != null ? "valorSuscripcion=" + valorSuscripcion + ", " : "") +
                (impuesto != null ? "impuesto=" + impuesto + ", " : "") +
                (categoriaGrupoId != null ? "categoriaGrupoId=" + categoriaGrupoId + ", " : "") +
            "}";
    }

}
