package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A RegistroFacturacion.
 */
@Entity
@Table(name = "registro_facturacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "registrofacturacion")
public class RegistroFacturacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "valor")
    private Integer valor;

    @Column(name = "fecha_registro")
    private Instant fechaRegistro;

    @Column(name = "fecha_facturacion")
    private LocalDate fechaFacturacion;

    @ManyToOne
    @JsonIgnoreProperties("registroFacturacions")
    private TipoRegistroCompra tipoRegistro;

    @ManyToOne
    @JsonIgnoreProperties("registroFacturacions")
    private Miembros miembro;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValor() {
        return valor;
    }

    public RegistroFacturacion valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Instant getFechaRegistro() {
        return fechaRegistro;
    }

    public RegistroFacturacion fechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
        return this;
    }

    public void setFechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDate getFechaFacturacion() {
        return fechaFacturacion;
    }

    public RegistroFacturacion fechaFacturacion(LocalDate fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
        return this;
    }

    public void setFechaFacturacion(LocalDate fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public TipoRegistroCompra getTipoRegistro() {
        return tipoRegistro;
    }

    public RegistroFacturacion tipoRegistro(TipoRegistroCompra tipoRegistroCompra) {
        this.tipoRegistro = tipoRegistroCompra;
        return this;
    }

    public void setTipoRegistro(TipoRegistroCompra tipoRegistroCompra) {
        this.tipoRegistro = tipoRegistroCompra;
    }

    public Miembros getMiembro() {
        return miembro;
    }

    public RegistroFacturacion miembro(Miembros miembros) {
        this.miembro = miembros;
        return this;
    }

    public void setMiembro(Miembros miembros) {
        this.miembro = miembros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistroFacturacion)) {
            return false;
        }
        return id != null && id.equals(((RegistroFacturacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RegistroFacturacion{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", fechaFacturacion='" + getFechaFacturacion() + "'" +
            "}";
    }
}
