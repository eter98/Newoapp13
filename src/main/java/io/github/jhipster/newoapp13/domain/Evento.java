package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import io.github.jhipster.newoapp13.domain.enumeration.TipoConsumod;

import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;

import io.github.jhipster.newoapp13.domain.enumeration.Categoriad;

/**
 * A Evento.
 */
@Entity
@Table(name = "evento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "evento")
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "nombre_evento", nullable = false)
    private String nombreEvento;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Lob
    @Column(name = "audio")
    private byte[] audio;

    @Column(name = "audio_content_type")
    private String audioContentType;

    @Lob
    @Column(name = "video")
    private byte[] video;

    @Column(name = "video_content_type")
    private String videoContentType;

    
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
    @Column(name = "tipo_consumo")
    private TipoConsumod tipoConsumo;

    @Column(name = "valor")
    private Float valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "impuesto")
    private Impuestod impuesto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_evento")
    private Categoriad tipoEvento;

    @Column(name = "evento_newo")
    private Boolean eventoNEWO;

    @Column(name = "web")
    private String web;

    @ManyToOne
    @JsonIgnoreProperties("eventos")
    private CategoriaContenidos categoriaEvento;

    @ManyToOne
    @JsonIgnoreProperties("eventos")
    private Grupos grupos;

    @ManyToOne
    @JsonIgnoreProperties("eventos")
    private Miembros miembro;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public Evento nombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
        return this;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Evento descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public Evento contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public byte[] getAudio() {
        return audio;
    }

    public Evento audio(byte[] audio) {
        this.audio = audio;
        return this;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }

    public String getAudioContentType() {
        return audioContentType;
    }

    public Evento audioContentType(String audioContentType) {
        this.audioContentType = audioContentType;
        return this;
    }

    public void setAudioContentType(String audioContentType) {
        this.audioContentType = audioContentType;
    }

    public byte[] getVideo() {
        return video;
    }

    public Evento video(byte[] video) {
        this.video = video;
        return this;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public String getVideoContentType() {
        return videoContentType;
    }

    public Evento videoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
        return this;
    }

    public void setVideoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
    }

    public byte[] getImagen1() {
        return imagen1;
    }

    public Evento imagen1(byte[] imagen1) {
        this.imagen1 = imagen1;
        return this;
    }

    public void setImagen1(byte[] imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen1ContentType() {
        return imagen1ContentType;
    }

    public Evento imagen1ContentType(String imagen1ContentType) {
        this.imagen1ContentType = imagen1ContentType;
        return this;
    }

    public void setImagen1ContentType(String imagen1ContentType) {
        this.imagen1ContentType = imagen1ContentType;
    }

    public byte[] getImagen2() {
        return imagen2;
    }

    public Evento imagen2(byte[] imagen2) {
        this.imagen2 = imagen2;
        return this;
    }

    public void setImagen2(byte[] imagen2) {
        this.imagen2 = imagen2;
    }

    public String getImagen2ContentType() {
        return imagen2ContentType;
    }

    public Evento imagen2ContentType(String imagen2ContentType) {
        this.imagen2ContentType = imagen2ContentType;
        return this;
    }

    public void setImagen2ContentType(String imagen2ContentType) {
        this.imagen2ContentType = imagen2ContentType;
    }

    public byte[] getBanner() {
        return banner;
    }

    public Evento banner(byte[] banner) {
        this.banner = banner;
        return this;
    }

    public void setBanner(byte[] banner) {
        this.banner = banner;
    }

    public String getBannerContentType() {
        return bannerContentType;
    }

    public Evento bannerContentType(String bannerContentType) {
        this.bannerContentType = bannerContentType;
        return this;
    }

    public void setBannerContentType(String bannerContentType) {
        this.bannerContentType = bannerContentType;
    }

    public TipoConsumod getTipoConsumo() {
        return tipoConsumo;
    }

    public Evento tipoConsumo(TipoConsumod tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
        return this;
    }

    public void setTipoConsumo(TipoConsumod tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public Float getValor() {
        return valor;
    }

    public Evento valor(Float valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Impuestod getImpuesto() {
        return impuesto;
    }

    public Evento impuesto(Impuestod impuesto) {
        this.impuesto = impuesto;
        return this;
    }

    public void setImpuesto(Impuestod impuesto) {
        this.impuesto = impuesto;
    }

    public Categoriad getTipoEvento() {
        return tipoEvento;
    }

    public Evento tipoEvento(Categoriad tipoEvento) {
        this.tipoEvento = tipoEvento;
        return this;
    }

    public void setTipoEvento(Categoriad tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Boolean isEventoNEWO() {
        return eventoNEWO;
    }

    public Evento eventoNEWO(Boolean eventoNEWO) {
        this.eventoNEWO = eventoNEWO;
        return this;
    }

    public void setEventoNEWO(Boolean eventoNEWO) {
        this.eventoNEWO = eventoNEWO;
    }

    public String getWeb() {
        return web;
    }

    public Evento web(String web) {
        this.web = web;
        return this;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public CategoriaContenidos getCategoriaEvento() {
        return categoriaEvento;
    }

    public Evento categoriaEvento(CategoriaContenidos categoriaContenidos) {
        this.categoriaEvento = categoriaContenidos;
        return this;
    }

    public void setCategoriaEvento(CategoriaContenidos categoriaContenidos) {
        this.categoriaEvento = categoriaContenidos;
    }

    public Grupos getGrupos() {
        return grupos;
    }

    public Evento grupos(Grupos grupos) {
        this.grupos = grupos;
        return this;
    }

    public void setGrupos(Grupos grupos) {
        this.grupos = grupos;
    }

    public Miembros getMiembro() {
        return miembro;
    }

    public Evento miembro(Miembros miembros) {
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
        if (!(o instanceof Evento)) {
            return false;
        }
        return id != null && id.equals(((Evento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Evento{" +
            "id=" + getId() +
            ", nombreEvento='" + getNombreEvento() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", audio='" + getAudio() + "'" +
            ", audioContentType='" + getAudioContentType() + "'" +
            ", video='" + getVideo() + "'" +
            ", videoContentType='" + getVideoContentType() + "'" +
            ", imagen1='" + getImagen1() + "'" +
            ", imagen1ContentType='" + getImagen1ContentType() + "'" +
            ", imagen2='" + getImagen2() + "'" +
            ", imagen2ContentType='" + getImagen2ContentType() + "'" +
            ", banner='" + getBanner() + "'" +
            ", bannerContentType='" + getBannerContentType() + "'" +
            ", tipoConsumo='" + getTipoConsumo() + "'" +
            ", valor=" + getValor() +
            ", impuesto='" + getImpuesto() + "'" +
            ", tipoEvento='" + getTipoEvento() + "'" +
            ", eventoNEWO='" + isEventoNEWO() + "'" +
            ", web='" + getWeb() + "'" +
            "}";
    }
}
