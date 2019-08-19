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

import io.github.jhipster.newoapp13.domain.enumeration.Categoriad;

import io.github.jhipster.newoapp13.domain.enumeration.EstadoPublicaciond;

import io.github.jhipster.newoapp13.domain.enumeration.TipoConsumod;

import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;

/**
 * A Blog.
 */
@Entity
@Table(name = "blog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contenido")
    private Categoriad tipoContenido;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Column(name = "fecha")
    private Instant fecha;

    @Lob
    @Column(name = "audio")
    private byte[] audio;

    @Column(name = "audio_content_type")
    private String audioContentType;

    @Column(name = "video")
    private String video;

    
    @Lob
    @Column(name = "imagen_1", nullable = false)
    private byte[] imagen1;

    @Column(name = "imagen_1_content_type", nullable = false)
    private String imagen1ContentType;

    @Lob
    @Column(name = "imagen_2")
    private byte[] imagen2;

    @Column(name = "imagen_2_content_type")
    private String imagen2ContentType;

    @Lob
    @Column(name = "banner")
    private byte[] banner;

    @Column(name = "banner_content_type")
    private String bannerContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_publicacion")
    private EstadoPublicaciond estadoPublicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_consumo")
    private TipoConsumod tipoConsumo;

    @Column(name = "valor")
    private Float valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "impuesto")
    private Impuestod impuesto;

    @ManyToOne
    @JsonIgnoreProperties("blogs")
    private Miembros miembro;

    @ManyToOne
    @JsonIgnoreProperties("blogs")
    private CategoriaContenidos categoriaBlog;

    @ManyToOne
    @JsonIgnoreProperties("blogs")
    private Grupos grupos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Blog titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Blog descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoriad getTipoContenido() {
        return tipoContenido;
    }

    public Blog tipoContenido(Categoriad tipoContenido) {
        this.tipoContenido = tipoContenido;
        return this;
    }

    public void setTipoContenido(Categoriad tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public String getContenido() {
        return contenido;
    }

    public Blog contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Blog fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public byte[] getAudio() {
        return audio;
    }

    public Blog audio(byte[] audio) {
        this.audio = audio;
        return this;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }

    public String getAudioContentType() {
        return audioContentType;
    }

    public Blog audioContentType(String audioContentType) {
        this.audioContentType = audioContentType;
        return this;
    }

    public void setAudioContentType(String audioContentType) {
        this.audioContentType = audioContentType;
    }

    public String getVideo() {
        return video;
    }

    public Blog video(String video) {
        this.video = video;
        return this;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public byte[] getImagen1() {
        return imagen1;
    }

    public Blog imagen1(byte[] imagen1) {
        this.imagen1 = imagen1;
        return this;
    }

    public void setImagen1(byte[] imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen1ContentType() {
        return imagen1ContentType;
    }

    public Blog imagen1ContentType(String imagen1ContentType) {
        this.imagen1ContentType = imagen1ContentType;
        return this;
    }

    public void setImagen1ContentType(String imagen1ContentType) {
        this.imagen1ContentType = imagen1ContentType;
    }

    public byte[] getImagen2() {
        return imagen2;
    }

    public Blog imagen2(byte[] imagen2) {
        this.imagen2 = imagen2;
        return this;
    }

    public void setImagen2(byte[] imagen2) {
        this.imagen2 = imagen2;
    }

    public String getImagen2ContentType() {
        return imagen2ContentType;
    }

    public Blog imagen2ContentType(String imagen2ContentType) {
        this.imagen2ContentType = imagen2ContentType;
        return this;
    }

    public void setImagen2ContentType(String imagen2ContentType) {
        this.imagen2ContentType = imagen2ContentType;
    }

    public byte[] getBanner() {
        return banner;
    }

    public Blog banner(byte[] banner) {
        this.banner = banner;
        return this;
    }

    public void setBanner(byte[] banner) {
        this.banner = banner;
    }

    public String getBannerContentType() {
        return bannerContentType;
    }

    public Blog bannerContentType(String bannerContentType) {
        this.bannerContentType = bannerContentType;
        return this;
    }

    public void setBannerContentType(String bannerContentType) {
        this.bannerContentType = bannerContentType;
    }

    public EstadoPublicaciond getEstadoPublicacion() {
        return estadoPublicacion;
    }

    public Blog estadoPublicacion(EstadoPublicaciond estadoPublicacion) {
        this.estadoPublicacion = estadoPublicacion;
        return this;
    }

    public void setEstadoPublicacion(EstadoPublicaciond estadoPublicacion) {
        this.estadoPublicacion = estadoPublicacion;
    }

    public TipoConsumod getTipoConsumo() {
        return tipoConsumo;
    }

    public Blog tipoConsumo(TipoConsumod tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
        return this;
    }

    public void setTipoConsumo(TipoConsumod tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public Float getValor() {
        return valor;
    }

    public Blog valor(Float valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Impuestod getImpuesto() {
        return impuesto;
    }

    public Blog impuesto(Impuestod impuesto) {
        this.impuesto = impuesto;
        return this;
    }

    public void setImpuesto(Impuestod impuesto) {
        this.impuesto = impuesto;
    }

    public Miembros getMiembro() {
        return miembro;
    }

    public Blog miembro(Miembros miembros) {
        this.miembro = miembros;
        return this;
    }

    public void setMiembro(Miembros miembros) {
        this.miembro = miembros;
    }

    public CategoriaContenidos getCategoriaBlog() {
        return categoriaBlog;
    }

    public Blog categoriaBlog(CategoriaContenidos categoriaContenidos) {
        this.categoriaBlog = categoriaContenidos;
        return this;
    }

    public void setCategoriaBlog(CategoriaContenidos categoriaContenidos) {
        this.categoriaBlog = categoriaContenidos;
    }

    public Grupos getGrupos() {
        return grupos;
    }

    public Blog grupos(Grupos grupos) {
        this.grupos = grupos;
        return this;
    }

    public void setGrupos(Grupos grupos) {
        this.grupos = grupos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Blog)) {
            return false;
        }
        return id != null && id.equals(((Blog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Blog{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", tipoContenido='" + getTipoContenido() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", audio='" + getAudio() + "'" +
            ", audioContentType='" + getAudioContentType() + "'" +
            ", video='" + getVideo() + "'" +
            ", imagen1='" + getImagen1() + "'" +
            ", imagen1ContentType='" + getImagen1ContentType() + "'" +
            ", imagen2='" + getImagen2() + "'" +
            ", imagen2ContentType='" + getImagen2ContentType() + "'" +
            ", banner='" + getBanner() + "'" +
            ", bannerContentType='" + getBannerContentType() + "'" +
            ", estadoPublicacion='" + getEstadoPublicacion() + "'" +
            ", tipoConsumo='" + getTipoConsumo() + "'" +
            ", valor=" + getValor() +
            ", impuesto='" + getImpuesto() + "'" +
            "}";
    }
}
