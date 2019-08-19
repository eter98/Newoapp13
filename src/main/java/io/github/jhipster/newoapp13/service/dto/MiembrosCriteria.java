package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.TipoDocumentod;
import io.github.jhipster.newoapp13.domain.enumeration.Generod;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Miembros} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.MiembrosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /miembros?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MiembrosCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TipoDocumentod
     */
    public static class TipoDocumentodFilter extends Filter<TipoDocumentod> {

        public TipoDocumentodFilter() {
        }

        public TipoDocumentodFilter(TipoDocumentodFilter filter) {
            super(filter);
        }

        @Override
        public TipoDocumentodFilter copy() {
            return new TipoDocumentodFilter(this);
        }

    }
    /**
     * Class for filtering Generod
     */
    public static class GenerodFilter extends Filter<Generod> {

        public GenerodFilter() {
        }

        public GenerodFilter(GenerodFilter filter) {
            super(filter);
        }

        @Override
        public GenerodFilter copy() {
            return new GenerodFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TipoDocumentodFilter tipoDocumento;

    private IntegerFilter identificacion;

    private LocalDateFilter fechaNacimiento;

    private LocalDateFilter fechaRegistro;

    private GenerodFilter genero;

    private StringFilter celular;

    private StringFilter conocimientosQueDomina;

    private StringFilter temasDeInteres;

    private StringFilter facebook;

    private StringFilter instagram;

    private StringFilter idGoogle;

    private StringFilter twiter;

    private BooleanFilter derechosDeCompra;

    private BooleanFilter accesoIlimitado;

    private BooleanFilter aliado;

    private BooleanFilter host;

    private LongFilter miembroId;

    private LongFilter nacionalidadId;

    private LongFilter sedeId;

    public MiembrosCriteria(){
    }

    public MiembrosCriteria(MiembrosCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.tipoDocumento = other.tipoDocumento == null ? null : other.tipoDocumento.copy();
        this.identificacion = other.identificacion == null ? null : other.identificacion.copy();
        this.fechaNacimiento = other.fechaNacimiento == null ? null : other.fechaNacimiento.copy();
        this.fechaRegistro = other.fechaRegistro == null ? null : other.fechaRegistro.copy();
        this.genero = other.genero == null ? null : other.genero.copy();
        this.celular = other.celular == null ? null : other.celular.copy();
        this.conocimientosQueDomina = other.conocimientosQueDomina == null ? null : other.conocimientosQueDomina.copy();
        this.temasDeInteres = other.temasDeInteres == null ? null : other.temasDeInteres.copy();
        this.facebook = other.facebook == null ? null : other.facebook.copy();
        this.instagram = other.instagram == null ? null : other.instagram.copy();
        this.idGoogle = other.idGoogle == null ? null : other.idGoogle.copy();
        this.twiter = other.twiter == null ? null : other.twiter.copy();
        this.derechosDeCompra = other.derechosDeCompra == null ? null : other.derechosDeCompra.copy();
        this.accesoIlimitado = other.accesoIlimitado == null ? null : other.accesoIlimitado.copy();
        this.aliado = other.aliado == null ? null : other.aliado.copy();
        this.host = other.host == null ? null : other.host.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
        this.nacionalidadId = other.nacionalidadId == null ? null : other.nacionalidadId.copy();
        this.sedeId = other.sedeId == null ? null : other.sedeId.copy();
    }

    @Override
    public MiembrosCriteria copy() {
        return new MiembrosCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TipoDocumentodFilter getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentodFilter tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public IntegerFilter getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(IntegerFilter identificacion) {
        this.identificacion = identificacion;
    }

    public LocalDateFilter getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateFilter fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDateFilter getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateFilter fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public GenerodFilter getGenero() {
        return genero;
    }

    public void setGenero(GenerodFilter genero) {
        this.genero = genero;
    }

    public StringFilter getCelular() {
        return celular;
    }

    public void setCelular(StringFilter celular) {
        this.celular = celular;
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

    public BooleanFilter getDerechosDeCompra() {
        return derechosDeCompra;
    }

    public void setDerechosDeCompra(BooleanFilter derechosDeCompra) {
        this.derechosDeCompra = derechosDeCompra;
    }

    public BooleanFilter getAccesoIlimitado() {
        return accesoIlimitado;
    }

    public void setAccesoIlimitado(BooleanFilter accesoIlimitado) {
        this.accesoIlimitado = accesoIlimitado;
    }

    public BooleanFilter getAliado() {
        return aliado;
    }

    public void setAliado(BooleanFilter aliado) {
        this.aliado = aliado;
    }

    public BooleanFilter getHost() {
        return host;
    }

    public void setHost(BooleanFilter host) {
        this.host = host;
    }

    public LongFilter getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(LongFilter miembroId) {
        this.miembroId = miembroId;
    }

    public LongFilter getNacionalidadId() {
        return nacionalidadId;
    }

    public void setNacionalidadId(LongFilter nacionalidadId) {
        this.nacionalidadId = nacionalidadId;
    }

    public LongFilter getSedeId() {
        return sedeId;
    }

    public void setSedeId(LongFilter sedeId) {
        this.sedeId = sedeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MiembrosCriteria that = (MiembrosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tipoDocumento, that.tipoDocumento) &&
            Objects.equals(identificacion, that.identificacion) &&
            Objects.equals(fechaNacimiento, that.fechaNacimiento) &&
            Objects.equals(fechaRegistro, that.fechaRegistro) &&
            Objects.equals(genero, that.genero) &&
            Objects.equals(celular, that.celular) &&
            Objects.equals(conocimientosQueDomina, that.conocimientosQueDomina) &&
            Objects.equals(temasDeInteres, that.temasDeInteres) &&
            Objects.equals(facebook, that.facebook) &&
            Objects.equals(instagram, that.instagram) &&
            Objects.equals(idGoogle, that.idGoogle) &&
            Objects.equals(twiter, that.twiter) &&
            Objects.equals(derechosDeCompra, that.derechosDeCompra) &&
            Objects.equals(accesoIlimitado, that.accesoIlimitado) &&
            Objects.equals(aliado, that.aliado) &&
            Objects.equals(host, that.host) &&
            Objects.equals(miembroId, that.miembroId) &&
            Objects.equals(nacionalidadId, that.nacionalidadId) &&
            Objects.equals(sedeId, that.sedeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tipoDocumento,
        identificacion,
        fechaNacimiento,
        fechaRegistro,
        genero,
        celular,
        conocimientosQueDomina,
        temasDeInteres,
        facebook,
        instagram,
        idGoogle,
        twiter,
        derechosDeCompra,
        accesoIlimitado,
        aliado,
        host,
        miembroId,
        nacionalidadId,
        sedeId
        );
    }

    @Override
    public String toString() {
        return "MiembrosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tipoDocumento != null ? "tipoDocumento=" + tipoDocumento + ", " : "") +
                (identificacion != null ? "identificacion=" + identificacion + ", " : "") +
                (fechaNacimiento != null ? "fechaNacimiento=" + fechaNacimiento + ", " : "") +
                (fechaRegistro != null ? "fechaRegistro=" + fechaRegistro + ", " : "") +
                (genero != null ? "genero=" + genero + ", " : "") +
                (celular != null ? "celular=" + celular + ", " : "") +
                (conocimientosQueDomina != null ? "conocimientosQueDomina=" + conocimientosQueDomina + ", " : "") +
                (temasDeInteres != null ? "temasDeInteres=" + temasDeInteres + ", " : "") +
                (facebook != null ? "facebook=" + facebook + ", " : "") +
                (instagram != null ? "instagram=" + instagram + ", " : "") +
                (idGoogle != null ? "idGoogle=" + idGoogle + ", " : "") +
                (twiter != null ? "twiter=" + twiter + ", " : "") +
                (derechosDeCompra != null ? "derechosDeCompra=" + derechosDeCompra + ", " : "") +
                (accesoIlimitado != null ? "accesoIlimitado=" + accesoIlimitado + ", " : "") +
                (aliado != null ? "aliado=" + aliado + ", " : "") +
                (host != null ? "host=" + host + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
                (nacionalidadId != null ? "nacionalidadId=" + nacionalidadId + ", " : "") +
                (sedeId != null ? "sedeId=" + sedeId + ", " : "") +
            "}";
    }

}
