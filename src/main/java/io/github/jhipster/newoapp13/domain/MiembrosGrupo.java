package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A MiembrosGrupo.
 */
@Entity
@Table(name = "miembros_grupo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "miembrosgrupo")
public class MiembrosGrupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("miembrosGrupos")
    private Miembros miembro;

    @ManyToOne
    @JsonIgnoreProperties("miembrosGrupos")
    private Grupos grupo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Miembros getMiembro() {
        return miembro;
    }

    public MiembrosGrupo miembro(Miembros miembros) {
        this.miembro = miembros;
        return this;
    }

    public void setMiembro(Miembros miembros) {
        this.miembro = miembros;
    }

    public Grupos getGrupo() {
        return grupo;
    }

    public MiembrosGrupo grupo(Grupos grupos) {
        this.grupo = grupos;
        return this;
    }

    public void setGrupo(Grupos grupos) {
        this.grupo = grupos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MiembrosGrupo)) {
            return false;
        }
        return id != null && id.equals(((MiembrosGrupo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MiembrosGrupo{" +
            "id=" + getId() +
            "}";
    }
}
