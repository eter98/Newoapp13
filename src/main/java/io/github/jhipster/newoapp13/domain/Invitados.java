package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import io.github.jhipster.newoapp13.domain.enumeration.TipoDocumentod;

/**
 * A Invitados.
 */
@Entity
@Table(name = "invitados")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "invitados")
public class Invitados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 3, max = 25)
    @Column(name = "nombre", length = 25, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 25)
    @Column(name = "apellido", length = 25, nullable = false)
    private String apellido;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumentod tipoDocumento;

    @NotNull
    @Size(min = 6, max = 12)
    @Column(name = "identificacion", length = 12, nullable = false)
    private String identificacion;

    @NotNull
    @Column(name = "correo", nullable = false)
    private String correo;

    @Size(min = 7, max = 12)
    @Column(name = "telefono", length = 12)
    private String telefono;

    @ManyToOne
    @JsonIgnoreProperties("invitados")
    private Miembros miembro;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Invitados nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Invitados apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public TipoDocumentod getTipoDocumento() {
        return tipoDocumento;
    }

    public Invitados tipoDocumento(TipoDocumentod tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(TipoDocumentod tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public Invitados identificacion(String identificacion) {
        this.identificacion = identificacion;
        return this;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public Invitados correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public Invitados telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Miembros getMiembro() {
        return miembro;
    }

    public Invitados miembro(Miembros miembros) {
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
        if (!(o instanceof Invitados)) {
            return false;
        }
        return id != null && id.equals(((Invitados) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Invitados{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", identificacion='" + getIdentificacion() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            "}";
    }
}
