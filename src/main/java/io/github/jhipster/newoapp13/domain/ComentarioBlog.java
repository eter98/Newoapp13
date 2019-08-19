package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ComentarioBlog.
 */
@Entity
@Table(name = "comentario_blog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "comentarioblog")
public class ComentarioBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "comentario", nullable = false)
    private String comentario;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "me_gusta")
    private Boolean meGusta;

    @Column(name = "seguir")
    private Boolean seguir;

    @ManyToOne
    @JsonIgnoreProperties("comentarioBlogs")
    private Blog blog;

    @ManyToOne
    @JsonIgnoreProperties("comentarioBlogs")
    private Miembros miembroComenta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public ComentarioBlog comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public ComentarioBlog fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean isMeGusta() {
        return meGusta;
    }

    public ComentarioBlog meGusta(Boolean meGusta) {
        this.meGusta = meGusta;
        return this;
    }

    public void setMeGusta(Boolean meGusta) {
        this.meGusta = meGusta;
    }

    public Boolean isSeguir() {
        return seguir;
    }

    public ComentarioBlog seguir(Boolean seguir) {
        this.seguir = seguir;
        return this;
    }

    public void setSeguir(Boolean seguir) {
        this.seguir = seguir;
    }

    public Blog getBlog() {
        return blog;
    }

    public ComentarioBlog blog(Blog blog) {
        this.blog = blog;
        return this;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Miembros getMiembroComenta() {
        return miembroComenta;
    }

    public ComentarioBlog miembroComenta(Miembros miembros) {
        this.miembroComenta = miembros;
        return this;
    }

    public void setMiembroComenta(Miembros miembros) {
        this.miembroComenta = miembros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComentarioBlog)) {
            return false;
        }
        return id != null && id.equals(((ComentarioBlog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ComentarioBlog{" +
            "id=" + getId() +
            ", comentario='" + getComentario() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", meGusta='" + isMeGusta() + "'" +
            ", seguir='" + isSeguir() + "'" +
            "}";
    }
}
