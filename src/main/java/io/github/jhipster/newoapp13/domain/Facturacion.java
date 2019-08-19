package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import io.github.jhipster.newoapp13.domain.enumeration.TipoPersonad;

import io.github.jhipster.newoapp13.domain.enumeration.PeriodicidadFacturaciond;

/**
 * A Facturacion.
 */
@Entity
@Table(name = "facturacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "facturacion")
public class Facturacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "titular_factura")
    private String titularFactura;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_persona")
    private TipoPersonad tipoPersona;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicidad_facturacion")
    private PeriodicidadFacturaciond periodicidadFacturacion;

    @Column(name = "maximo_monto")
    private Integer maximoMonto;

    @Column(name = "valor")
    private Integer valor;

    @ManyToOne
    @JsonIgnoreProperties("facturacions")
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties("facturacions")
    private CuentaAsociada identificacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitularFactura() {
        return titularFactura;
    }

    public Facturacion titularFactura(String titularFactura) {
        this.titularFactura = titularFactura;
        return this;
    }

    public void setTitularFactura(String titularFactura) {
        this.titularFactura = titularFactura;
    }

    public TipoPersonad getTipoPersona() {
        return tipoPersona;
    }

    public Facturacion tipoPersona(TipoPersonad tipoPersona) {
        this.tipoPersona = tipoPersona;
        return this;
    }

    public void setTipoPersona(TipoPersonad tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public PeriodicidadFacturaciond getPeriodicidadFacturacion() {
        return periodicidadFacturacion;
    }

    public Facturacion periodicidadFacturacion(PeriodicidadFacturaciond periodicidadFacturacion) {
        this.periodicidadFacturacion = periodicidadFacturacion;
        return this;
    }

    public void setPeriodicidadFacturacion(PeriodicidadFacturaciond periodicidadFacturacion) {
        this.periodicidadFacturacion = periodicidadFacturacion;
    }

    public Integer getMaximoMonto() {
        return maximoMonto;
    }

    public Facturacion maximoMonto(Integer maximoMonto) {
        this.maximoMonto = maximoMonto;
        return this;
    }

    public void setMaximoMonto(Integer maximoMonto) {
        this.maximoMonto = maximoMonto;
    }

    public Integer getValor() {
        return valor;
    }

    public Facturacion valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Facturacion empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public CuentaAsociada getIdentificacion() {
        return identificacion;
    }

    public Facturacion identificacion(CuentaAsociada cuentaAsociada) {
        this.identificacion = cuentaAsociada;
        return this;
    }

    public void setIdentificacion(CuentaAsociada cuentaAsociada) {
        this.identificacion = cuentaAsociada;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facturacion)) {
            return false;
        }
        return id != null && id.equals(((Facturacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Facturacion{" +
            "id=" + getId() +
            ", titularFactura='" + getTitularFactura() + "'" +
            ", tipoPersona='" + getTipoPersona() + "'" +
            ", periodicidadFacturacion='" + getPeriodicidadFacturacion() + "'" +
            ", maximoMonto=" + getMaximoMonto() +
            ", valor=" + getValor() +
            "}";
    }
}
