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
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Sedes} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.SedesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sedes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SedesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombreSede;

    private DoubleFilter coordenadaX;

    private DoubleFilter coordenadaY;

    private StringFilter direccion;

    private StringFilter telefonoComunidad;

    private StringFilter telefonoNegocio;

    private StringFilter horario;

    private LongFilter ciudadId;

    public SedesCriteria(){
    }

    public SedesCriteria(SedesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombreSede = other.nombreSede == null ? null : other.nombreSede.copy();
        this.coordenadaX = other.coordenadaX == null ? null : other.coordenadaX.copy();
        this.coordenadaY = other.coordenadaY == null ? null : other.coordenadaY.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.telefonoComunidad = other.telefonoComunidad == null ? null : other.telefonoComunidad.copy();
        this.telefonoNegocio = other.telefonoNegocio == null ? null : other.telefonoNegocio.copy();
        this.horario = other.horario == null ? null : other.horario.copy();
        this.ciudadId = other.ciudadId == null ? null : other.ciudadId.copy();
    }

    @Override
    public SedesCriteria copy() {
        return new SedesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(StringFilter nombreSede) {
        this.nombreSede = nombreSede;
    }

    public DoubleFilter getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(DoubleFilter coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public DoubleFilter getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(DoubleFilter coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public StringFilter getDireccion() {
        return direccion;
    }

    public void setDireccion(StringFilter direccion) {
        this.direccion = direccion;
    }

    public StringFilter getTelefonoComunidad() {
        return telefonoComunidad;
    }

    public void setTelefonoComunidad(StringFilter telefonoComunidad) {
        this.telefonoComunidad = telefonoComunidad;
    }

    public StringFilter getTelefonoNegocio() {
        return telefonoNegocio;
    }

    public void setTelefonoNegocio(StringFilter telefonoNegocio) {
        this.telefonoNegocio = telefonoNegocio;
    }

    public StringFilter getHorario() {
        return horario;
    }

    public void setHorario(StringFilter horario) {
        this.horario = horario;
    }

    public LongFilter getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(LongFilter ciudadId) {
        this.ciudadId = ciudadId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SedesCriteria that = (SedesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombreSede, that.nombreSede) &&
            Objects.equals(coordenadaX, that.coordenadaX) &&
            Objects.equals(coordenadaY, that.coordenadaY) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(telefonoComunidad, that.telefonoComunidad) &&
            Objects.equals(telefonoNegocio, that.telefonoNegocio) &&
            Objects.equals(horario, that.horario) &&
            Objects.equals(ciudadId, that.ciudadId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombreSede,
        coordenadaX,
        coordenadaY,
        direccion,
        telefonoComunidad,
        telefonoNegocio,
        horario,
        ciudadId
        );
    }

    @Override
    public String toString() {
        return "SedesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombreSede != null ? "nombreSede=" + nombreSede + ", " : "") +
                (coordenadaX != null ? "coordenadaX=" + coordenadaX + ", " : "") +
                (coordenadaY != null ? "coordenadaY=" + coordenadaY + ", " : "") +
                (direccion != null ? "direccion=" + direccion + ", " : "") +
                (telefonoComunidad != null ? "telefonoComunidad=" + telefonoComunidad + ", " : "") +
                (telefonoNegocio != null ? "telefonoNegocio=" + telefonoNegocio + ", " : "") +
                (horario != null ? "horario=" + horario + ", " : "") +
                (ciudadId != null ? "ciudadId=" + ciudadId + ", " : "") +
            "}";
    }

}
