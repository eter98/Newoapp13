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
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.PerfilEquipoEmpresa} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.PerfilEquipoEmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /perfil-equipo-empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PerfilEquipoEmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter conocimientosQueDomina;

    private StringFilter temasDeInteres;

    private StringFilter facebook;

    private StringFilter instagram;

    private StringFilter idGoogle;

    private StringFilter twiter;

    private LongFilter empresaId;

    public PerfilEquipoEmpresaCriteria(){
    }

    public PerfilEquipoEmpresaCriteria(PerfilEquipoEmpresaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.conocimientosQueDomina = other.conocimientosQueDomina == null ? null : other.conocimientosQueDomina.copy();
        this.temasDeInteres = other.temasDeInteres == null ? null : other.temasDeInteres.copy();
        this.facebook = other.facebook == null ? null : other.facebook.copy();
        this.instagram = other.instagram == null ? null : other.instagram.copy();
        this.idGoogle = other.idGoogle == null ? null : other.idGoogle.copy();
        this.twiter = other.twiter == null ? null : other.twiter.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
    }

    @Override
    public PerfilEquipoEmpresaCriteria copy() {
        return new PerfilEquipoEmpresaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getConocimientosQueDomina() {
        return conocimientosQueDomina;
    }

    public void setConocimientosQueDomina(StringFilter conocimientosQueDomina) {
        this.conocimientosQueDomina = conocimientosQueDomina;
    }

    public StringFilter getTemasDeInteres() {
        return temasDeInteres;
    }

    public void setTemasDeInteres(StringFilter temasDeInteres) {
        this.temasDeInteres = temasDeInteres;
    }

    public StringFilter getFacebook() {
        return facebook;
    }

    public void setFacebook(StringFilter facebook) {
        this.facebook = facebook;
    }

    public StringFilter getInstagram() {
        return instagram;
    }

    public void setInstagram(StringFilter instagram) {
        this.instagram = instagram;
    }

    public StringFilter getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(StringFilter idGoogle) {
        this.idGoogle = idGoogle;
    }

    public StringFilter getTwiter() {
        return twiter;
    }

    public void setTwiter(StringFilter twiter) {
        this.twiter = twiter;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PerfilEquipoEmpresaCriteria that = (PerfilEquipoEmpresaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(conocimientosQueDomina, that.conocimientosQueDomina) &&
            Objects.equals(temasDeInteres, that.temasDeInteres) &&
            Objects.equals(facebook, that.facebook) &&
            Objects.equals(instagram, that.instagram) &&
            Objects.equals(idGoogle, that.idGoogle) &&
            Objects.equals(twiter, that.twiter) &&
            Objects.equals(empresaId, that.empresaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        conocimientosQueDomina,
        temasDeInteres,
        facebook,
        instagram,
        idGoogle,
        twiter,
        empresaId
        );
    }

    @Override
    public String toString() {
        return "PerfilEquipoEmpresaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (conocimientosQueDomina != null ? "conocimientosQueDomina=" + conocimientosQueDomina + ", " : "") +
                (temasDeInteres != null ? "temasDeInteres=" + temasDeInteres + ", " : "") +
                (facebook != null ? "facebook=" + facebook + ", " : "") +
                (instagram != null ? "instagram=" + instagram + ", " : "") +
                (idGoogle != null ? "idGoogle=" + idGoogle + ", " : "") +
                (twiter != null ? "twiter=" + twiter + ", " : "") +
                (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            "}";
    }

}
