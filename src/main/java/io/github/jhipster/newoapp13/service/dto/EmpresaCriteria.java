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
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Empresa} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.EmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter razonSocial;

    private StringFilter nit;

    private StringFilter direccion;

    private StringFilter telefono;

    private StringFilter correo;

    private StringFilter web;

    private StringFilter celular;

    private StringFilter facebook;

    private StringFilter instagram;

    private StringFilter idGoogle;

    private StringFilter twiter;

    private StringFilter conocimientosQueDomina;

    private StringFilter temasDeInteres;

    private LongFilter miembroId;

    public EmpresaCriteria(){
    }

    public EmpresaCriteria(EmpresaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.razonSocial = other.razonSocial == null ? null : other.razonSocial.copy();
        this.nit = other.nit == null ? null : other.nit.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.correo = other.correo == null ? null : other.correo.copy();
        this.web = other.web == null ? null : other.web.copy();
        this.celular = other.celular == null ? null : other.celular.copy();
        this.facebook = other.facebook == null ? null : other.facebook.copy();
        this.instagram = other.instagram == null ? null : other.instagram.copy();
        this.idGoogle = other.idGoogle == null ? null : other.idGoogle.copy();
        this.twiter = other.twiter == null ? null : other.twiter.copy();
        this.conocimientosQueDomina = other.conocimientosQueDomina == null ? null : other.conocimientosQueDomina.copy();
        this.temasDeInteres = other.temasDeInteres == null ? null : other.temasDeInteres.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
    }

    @Override
    public EmpresaCriteria copy() {
        return new EmpresaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(StringFilter razonSocial) {
        this.razonSocial = razonSocial;
    }

    public StringFilter getNit() {
        return nit;
    }

    public void setNit(StringFilter nit) {
        this.nit = nit;
    }

    public StringFilter getDireccion() {
        return direccion;
    }

    public void setDireccion(StringFilter direccion) {
        this.direccion = direccion;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }

    public StringFilter getCorreo() {
        return correo;
    }

    public void setCorreo(StringFilter correo) {
        this.correo = correo;
    }

    public StringFilter getWeb() {
        return web;
    }

    public void setWeb(StringFilter web) {
        this.web = web;
    }

    public StringFilter getCelular() {
        return celular;
    }

    public void setCelular(StringFilter celular) {
        this.celular = celular;
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
        final EmpresaCriteria that = (EmpresaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(razonSocial, that.razonSocial) &&
            Objects.equals(nit, that.nit) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(correo, that.correo) &&
            Objects.equals(web, that.web) &&
            Objects.equals(celular, that.celular) &&
            Objects.equals(facebook, that.facebook) &&
            Objects.equals(instagram, that.instagram) &&
            Objects.equals(idGoogle, that.idGoogle) &&
            Objects.equals(twiter, that.twiter) &&
            Objects.equals(conocimientosQueDomina, that.conocimientosQueDomina) &&
            Objects.equals(temasDeInteres, that.temasDeInteres) &&
            Objects.equals(miembroId, that.miembroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        razonSocial,
        nit,
        direccion,
        telefono,
        correo,
        web,
        celular,
        facebook,
        instagram,
        idGoogle,
        twiter,
        conocimientosQueDomina,
        temasDeInteres,
        miembroId
        );
    }

    @Override
    public String toString() {
        return "EmpresaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (razonSocial != null ? "razonSocial=" + razonSocial + ", " : "") +
                (nit != null ? "nit=" + nit + ", " : "") +
                (direccion != null ? "direccion=" + direccion + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (correo != null ? "correo=" + correo + ", " : "") +
                (web != null ? "web=" + web + ", " : "") +
                (celular != null ? "celular=" + celular + ", " : "") +
                (facebook != null ? "facebook=" + facebook + ", " : "") +
                (instagram != null ? "instagram=" + instagram + ", " : "") +
                (idGoogle != null ? "idGoogle=" + idGoogle + ", " : "") +
                (twiter != null ? "twiter=" + twiter + ", " : "") +
                (conocimientosQueDomina != null ? "conocimientosQueDomina=" + conocimientosQueDomina + ", " : "") +
                (temasDeInteres != null ? "temasDeInteres=" + temasDeInteres + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
            "}";
    }

}
