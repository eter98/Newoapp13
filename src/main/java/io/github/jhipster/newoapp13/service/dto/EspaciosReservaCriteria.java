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

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.EspaciosReserva} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.EspaciosReservaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /espacios-reservas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EspaciosReservaCriteria implements Serializable, Criteria {
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

    private StringFilter nombre;

    private StringFilter descripcion;

    private StringFilter facilidades;

    private IntegerFilter capacidad;

    private StringFilter apertura;

    private StringFilter cierre;

    private StringFilter wifi;

    private StringFilter video;

    private IntegerFilter tarifa1Hora;

    private IntegerFilter tarifa2Hora;

    private IntegerFilter tarifa3Hora;

    private IntegerFilter tarifa4Hora;

    private IntegerFilter tarifa5Hora;

    private IntegerFilter tarifa6Hora;

    private IntegerFilter tarifa7Hora;

    private IntegerFilter tarifa8Hora;

    private ImpuestodFilter impuesto;

    private LongFilter sedeId;

    public EspaciosReservaCriteria(){
    }

    public EspaciosReservaCriteria(EspaciosReservaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.facilidades = other.facilidades == null ? null : other.facilidades.copy();
        this.capacidad = other.capacidad == null ? null : other.capacidad.copy();
        this.apertura = other.apertura == null ? null : other.apertura.copy();
        this.cierre = other.cierre == null ? null : other.cierre.copy();
        this.wifi = other.wifi == null ? null : other.wifi.copy();
        this.video = other.video == null ? null : other.video.copy();
        this.tarifa1Hora = other.tarifa1Hora == null ? null : other.tarifa1Hora.copy();
        this.tarifa2Hora = other.tarifa2Hora == null ? null : other.tarifa2Hora.copy();
        this.tarifa3Hora = other.tarifa3Hora == null ? null : other.tarifa3Hora.copy();
        this.tarifa4Hora = other.tarifa4Hora == null ? null : other.tarifa4Hora.copy();
        this.tarifa5Hora = other.tarifa5Hora == null ? null : other.tarifa5Hora.copy();
        this.tarifa6Hora = other.tarifa6Hora == null ? null : other.tarifa6Hora.copy();
        this.tarifa7Hora = other.tarifa7Hora == null ? null : other.tarifa7Hora.copy();
        this.tarifa8Hora = other.tarifa8Hora == null ? null : other.tarifa8Hora.copy();
        this.impuesto = other.impuesto == null ? null : other.impuesto.copy();
        this.sedeId = other.sedeId == null ? null : other.sedeId.copy();
    }

    @Override
    public EspaciosReservaCriteria copy() {
        return new EspaciosReservaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public StringFilter getFacilidades() {
        return facilidades;
    }

    public void setFacilidades(StringFilter facilidades) {
        this.facilidades = facilidades;
    }

    public IntegerFilter getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(IntegerFilter capacidad) {
        this.capacidad = capacidad;
    }

    public StringFilter getApertura() {
        return apertura;
    }

    public void setApertura(StringFilter apertura) {
        this.apertura = apertura;
    }

    public StringFilter getCierre() {
        return cierre;
    }

    public void setCierre(StringFilter cierre) {
        this.cierre = cierre;
    }

    public StringFilter getWifi() {
        return wifi;
    }

    public void setWifi(StringFilter wifi) {
        this.wifi = wifi;
    }

    public StringFilter getVideo() {
        return video;
    }

    public void setVideo(StringFilter video) {
        this.video = video;
    }

    public IntegerFilter getTarifa1Hora() {
        return tarifa1Hora;
    }

    public void setTarifa1Hora(IntegerFilter tarifa1Hora) {
        this.tarifa1Hora = tarifa1Hora;
    }

    public IntegerFilter getTarifa2Hora() {
        return tarifa2Hora;
    }

    public void setTarifa2Hora(IntegerFilter tarifa2Hora) {
        this.tarifa2Hora = tarifa2Hora;
    }

    public IntegerFilter getTarifa3Hora() {
        return tarifa3Hora;
    }

    public void setTarifa3Hora(IntegerFilter tarifa3Hora) {
        this.tarifa3Hora = tarifa3Hora;
    }

    public IntegerFilter getTarifa4Hora() {
        return tarifa4Hora;
    }

    public void setTarifa4Hora(IntegerFilter tarifa4Hora) {
        this.tarifa4Hora = tarifa4Hora;
    }

    public IntegerFilter getTarifa5Hora() {
        return tarifa5Hora;
    }

    public void setTarifa5Hora(IntegerFilter tarifa5Hora) {
        this.tarifa5Hora = tarifa5Hora;
    }

    public IntegerFilter getTarifa6Hora() {
        return tarifa6Hora;
    }

    public void setTarifa6Hora(IntegerFilter tarifa6Hora) {
        this.tarifa6Hora = tarifa6Hora;
    }

    public IntegerFilter getTarifa7Hora() {
        return tarifa7Hora;
    }

    public void setTarifa7Hora(IntegerFilter tarifa7Hora) {
        this.tarifa7Hora = tarifa7Hora;
    }

    public IntegerFilter getTarifa8Hora() {
        return tarifa8Hora;
    }

    public void setTarifa8Hora(IntegerFilter tarifa8Hora) {
        this.tarifa8Hora = tarifa8Hora;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EspaciosReservaCriteria that = (EspaciosReservaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(facilidades, that.facilidades) &&
            Objects.equals(capacidad, that.capacidad) &&
            Objects.equals(apertura, that.apertura) &&
            Objects.equals(cierre, that.cierre) &&
            Objects.equals(wifi, that.wifi) &&
            Objects.equals(video, that.video) &&
            Objects.equals(tarifa1Hora, that.tarifa1Hora) &&
            Objects.equals(tarifa2Hora, that.tarifa2Hora) &&
            Objects.equals(tarifa3Hora, that.tarifa3Hora) &&
            Objects.equals(tarifa4Hora, that.tarifa4Hora) &&
            Objects.equals(tarifa5Hora, that.tarifa5Hora) &&
            Objects.equals(tarifa6Hora, that.tarifa6Hora) &&
            Objects.equals(tarifa7Hora, that.tarifa7Hora) &&
            Objects.equals(tarifa8Hora, that.tarifa8Hora) &&
            Objects.equals(impuesto, that.impuesto) &&
            Objects.equals(sedeId, that.sedeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        descripcion,
        facilidades,
        capacidad,
        apertura,
        cierre,
        wifi,
        video,
        tarifa1Hora,
        tarifa2Hora,
        tarifa3Hora,
        tarifa4Hora,
        tarifa5Hora,
        tarifa6Hora,
        tarifa7Hora,
        tarifa8Hora,
        impuesto,
        sedeId
        );
    }

    @Override
    public String toString() {
        return "EspaciosReservaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (facilidades != null ? "facilidades=" + facilidades + ", " : "") +
                (capacidad != null ? "capacidad=" + capacidad + ", " : "") +
                (apertura != null ? "apertura=" + apertura + ", " : "") +
                (cierre != null ? "cierre=" + cierre + ", " : "") +
                (wifi != null ? "wifi=" + wifi + ", " : "") +
                (video != null ? "video=" + video + ", " : "") +
                (tarifa1Hora != null ? "tarifa1Hora=" + tarifa1Hora + ", " : "") +
                (tarifa2Hora != null ? "tarifa2Hora=" + tarifa2Hora + ", " : "") +
                (tarifa3Hora != null ? "tarifa3Hora=" + tarifa3Hora + ", " : "") +
                (tarifa4Hora != null ? "tarifa4Hora=" + tarifa4Hora + ", " : "") +
                (tarifa5Hora != null ? "tarifa5Hora=" + tarifa5Hora + ", " : "") +
                (tarifa6Hora != null ? "tarifa6Hora=" + tarifa6Hora + ", " : "") +
                (tarifa7Hora != null ? "tarifa7Hora=" + tarifa7Hora + ", " : "") +
                (tarifa8Hora != null ? "tarifa8Hora=" + tarifa8Hora + ", " : "") +
                (impuesto != null ? "impuesto=" + impuesto + ", " : "") +
                (sedeId != null ? "sedeId=" + sedeId + ", " : "") +
            "}";
    }

}
