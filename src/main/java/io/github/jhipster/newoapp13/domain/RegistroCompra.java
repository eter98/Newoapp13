package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A RegistroCompra.
 */
@Entity
@Table(name = "registro_compra")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "registrocompra")
public class RegistroCompra implements Serializable {

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

    @Column(name = "id_transaccion")
    private Integer idTransaccion;

    @ManyToOne
    @JsonIgnoreProperties("registroCompras")
    private Miembros miembro;

    @ManyToOne
    @JsonIgnoreProperties("registroCompras")
    private TipoRegistroCompra tipoRegistro;

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

    public RegistroCompra valor(Integer valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Instant getFechaRegistro() {
        return fechaRegistro;
    }

    public RegistroCompra fechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
        return this;
    }

    public void setFechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public RegistroCompra idTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
        return this;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Miembros getMiembro() {
        return miembro;
    }

    public RegistroCompra miembro(Miembros miembros) {
        this.miembro = miembros;
        return this;
    }

    public void setMiembro(Miembros miembros) {
        this.miembro = miembros;
    }

    public TipoRegistroCompra getTipoRegistro() {
        return tipoRegistro;
    }

    public RegistroCompra tipoRegistro(TipoRegistroCompra tipoRegistroCompra) {
        this.tipoRegistro = tipoRegistroCompra;
        return this;
    }

    public void setTipoRegistro(TipoRegistroCompra tipoRegistroCompra) {
        this.tipoRegistro = tipoRegistroCompra;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistroCompra)) {
            return false;
        }
        return id != null && id.equals(((RegistroCompra) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RegistroCompra{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", idTransaccion=" + getIdTransaccion() +
            "}";
    }
}
