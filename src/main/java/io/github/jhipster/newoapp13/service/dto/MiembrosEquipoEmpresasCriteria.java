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
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.MiembrosEquipoEmpresas} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.MiembrosEquipoEmpresasResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /miembros-equipo-empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MiembrosEquipoEmpresasCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter equipoId;

    private LongFilter miembroId;

    public MiembrosEquipoEmpresasCriteria(){
    }

    public MiembrosEquipoEmpresasCriteria(MiembrosEquipoEmpresasCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.equipoId = other.equipoId == null ? null : other.equipoId.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
    }

    @Override
    public MiembrosEquipoEmpresasCriteria copy() {
        return new MiembrosEquipoEmpresasCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(LongFilter equipoId) {
        this.equipoId = equipoId;
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
        final MiembrosEquipoEmpresasCriteria that = (MiembrosEquipoEmpresasCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(equipoId, that.equipoId) &&
            Objects.equals(miembroId, that.miembroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        equipoId,
        miembroId
        );
    }

    @Override
    public String toString() {
        return "MiembrosEquipoEmpresasCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (equipoId != null ? "equipoId=" + equipoId + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
            "}";
    }

}
