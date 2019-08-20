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
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.ProductosServicios} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.ProductosServiciosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /productos-servicios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductosServiciosCriteria implements Serializable, Criteria {
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

    private StringFilter nombreProducto;

    private StringFilter descripcion;

    private BooleanFilter inventariables;

    private IntegerFilter valor;

    private ImpuestodFilter impuesto;

    private StringFilter video;

    private StringFilter web;

    private LongFilter miembroId;

    public ProductosServiciosCriteria(){
    }

    public ProductosServiciosCriteria(ProductosServiciosCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombreProducto = other.nombreProducto == null ? null : other.nombreProducto.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.inventariables = other.inventariables == null ? null : other.inventariables.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.impuesto = other.impuesto == null ? null : other.impuesto.copy();
        this.video = other.video == null ? null : other.video.copy();
        this.web = other.web == null ? null : other.web.copy();
        this.miembroId = other.miembroId == null ? null : other.miembroId.copy();
    }

    @Override
    public ProductosServiciosCriteria copy() {
        return new ProductosServiciosCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(StringFilter nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public BooleanFilter getInventariables() {
        return inventariables;
    }

    public void setInventariables(BooleanFilter inventariables) {
        this.inventariables = inventariables;
    }

    public IntegerFilter getValor() {
        return valor;
    }

    public void setValor(IntegerFilter valor) {
        this.valor = valor;
    }

    public ImpuestodFilter getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(ImpuestodFilter impuesto) {
        this.impuesto = impuesto;
    }

    public StringFilter getVideo() {
        return video;
    }

    public void setVideo(StringFilter video) {
        this.video = video;
    }

    public StringFilter getWeb() {
        return web;
    }

    public void setWeb(StringFilter web) {
        this.web = web;
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
        final ProductosServiciosCriteria that = (ProductosServiciosCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombreProducto, that.nombreProducto) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(inventariables, that.inventariables) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(impuesto, that.impuesto) &&
            Objects.equals(video, that.video) &&
            Objects.equals(web, that.web) &&
            Objects.equals(miembroId, that.miembroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombreProducto,
        descripcion,
        inventariables,
        valor,
        impuesto,
        video,
        web,
        miembroId
        );
    }

    @Override
    public String toString() {
        return "ProductosServiciosCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombreProducto != null ? "nombreProducto=" + nombreProducto + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (inventariables != null ? "inventariables=" + inventariables + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (impuesto != null ? "impuesto=" + impuesto + ", " : "") +
                (video != null ? "video=" + video + ", " : "") +
                (web != null ? "web=" + web + ", " : "") +
                (miembroId != null ? "miembroId=" + miembroId + ", " : "") +
            "}";
    }

}
