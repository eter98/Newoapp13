package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A ComentarioFeed.
 */
@Entity
@Table(name = "comentario_feed")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "comentariofeed")
public class ComentarioFeed implements Serializable {

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
    private Instant fecha;

    @Column(name = "me_gusta")
    private Boolean meGusta;

    @Column(name = "seguir")
    private Boolean seguir;

    @ManyToOne
    @JsonIgnoreProperties("comentarioFeeds")
    private Feed feed;

    @ManyToOne
    @JsonIgnoreProperties("comentarioFeeds")
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

    public ComentarioFeed comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Instant getFecha() {
        return fecha;
    }

    public ComentarioFeed fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Boolean isMeGusta() {
        return meGusta;
    }

    public ComentarioFeed meGusta(Boolean meGusta) {
        this.meGusta = meGusta;
        return this;
    }

    public void setMeGusta(Boolean meGusta) {
        this.meGusta = meGusta;
    }

    public Boolean isSeguir() {
        return seguir;
    }

    public ComentarioFeed seguir(Boolean seguir) {
        this.seguir = seguir;
        return this;
    }

    public void setSeguir(Boolean seguir) {
        this.seguir = seguir;
    }

    public Feed getFeed() {
        return feed;
    }

    public ComentarioFeed feed(Feed feed) {
        this.feed = feed;
        return this;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Miembros getMiembroComenta() {
        return miembroComenta;
    }

    public ComentarioFeed miembroComenta(Miembros miembros) {
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
        if (!(o instanceof ComentarioFeed)) {
            return false;
        }
        return id != null && id.equals(((ComentarioFeed) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ComentarioFeed{" +
            "id=" + getId() +
            ", comentario='" + getComentario() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", meGusta='" + isMeGusta() + "'" +
            ", seguir='" + isSeguir() + "'" +
            "}";
    }
}
