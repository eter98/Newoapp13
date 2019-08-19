package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import io.github.jhipster.newoapp13.domain.enumeration.Beneficiosd;

/**
 * A Beneficio.
 */
@Entity
@Table(name = "beneficio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "beneficio")
public class Beneficio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_beneficio", nullable = false)
    private Beneficiosd tipoBeneficio;

    @NotNull
    @Column(name = "descuento", nullable = false)
    private Integer descuento;

    @ManyToOne
    @JsonIgnoreProperties("beneficios")
    private Miembros miembro;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Beneficiosd getTipoBeneficio() {
        return tipoBeneficio;
    }

    public Beneficio tipoBeneficio(Beneficiosd tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
        return this;
    }

    public void setTipoBeneficio(Beneficiosd tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public Beneficio descuento(Integer descuento) {
        this.descuento = descuento;
        return this;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public Miembros getMiembro() {
        return miembro;
    }

    public Beneficio miembro(Miembros miembros) {
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
        if (!(o instanceof Beneficio)) {
            return false;
        }
        return id != null && id.equals(((Beneficio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Beneficio{" +
            "id=" + getId() +
            ", tipoBeneficio='" + getTipoBeneficio() + "'" +
            ", descuento=" + getDescuento() +
            "}";
    }
}
