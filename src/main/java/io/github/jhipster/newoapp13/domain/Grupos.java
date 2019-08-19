package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import io.github.jhipster.newoapp13.domain.enumeration.TipoGrupod;

import io.github.jhipster.newoapp13.domain.enumeration.TipoConsumod;

import io.github.jhipster.newoapp13.domain.enumeration.Impuestod;

/**
 * A Grupos.
 */
@Entity
@Table(name = "grupos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "grupos")
public class Grupos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "nombre_grupo", nullable = false)
    private String nombreGrupo;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "twiter")
    private String twiter;

    @Column(name = "linked_in")
    private String linkedIn;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_grupo")
    private TipoGrupod tipoGrupo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_consumo")
    private TipoConsumod tipoConsumo;

    @Column(name = "valor_suscripcion")
    private Integer valorSuscripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "impuesto")
    private Impuestod impuesto;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "reglas_grupo")
    private String reglasGrupo;

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

    @ManyToOne
    @JsonIgnoreProperties("grupos")
    private CategoriaContenidos categoriaGrupo;

    @ManyToOne
    @JsonIgnoreProperties("grupos")
    private Evento evento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public Grupos nombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
        return this;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getInstagram() {
        return instagram;
    }

    public Grupos instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public Grupos facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwiter() {
        return twiter;
    }

    public Grupos twiter(String twiter) {
        this.twiter = twiter;
        return this;
    }

    public void setTwiter(String twiter) {
        this.twiter = twiter;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public Grupos linkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public TipoGrupod getTipoGrupo() {
        return tipoGrupo;
    }

    public Grupos tipoGrupo(TipoGrupod tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
        return this;
    }

    public void setTipoGrupo(TipoGrupod tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }

    public TipoConsumod getTipoConsumo() {
        return tipoConsumo;
    }

    public Grupos tipoConsumo(TipoConsumod tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
        return this;
    }

    public void setTipoConsumo(TipoConsumod tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public Integer getValorSuscripcion() {
        return valorSuscripcion;
    }

    public Grupos valorSuscripcion(Integer valorSuscripcion) {
        this.valorSuscripcion = valorSuscripcion;
        return this;
    }

    public void setValorSuscripcion(Integer valorSuscripcion) {
        this.valorSuscripcion = valorSuscripcion;
    }

    public Impuestod getImpuesto() {
        return impuesto;
    }

    public Grupos impuesto(Impuestod impuesto) {
        this.impuesto = impuesto;
        return this;
    }

    public void setImpuesto(Impuestod impuesto) {
        this.impuesto = impuesto;
    }

    public String getReglasGrupo() {
        return reglasGrupo;
    }

    public Grupos reglasGrupo(String reglasGrupo) {
        this.reglasGrupo = reglasGrupo;
        return this;
    }

    public void setReglasGrupo(String reglasGrupo) {
        this.reglasGrupo = reglasGrupo;
    }

    public byte[] getAudio() {
        return audio;
    }

    public Grupos audio(byte[] audio) {
        this.audio = audio;
        return this;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }

    public String getAudioContentType() {
        return audioContentType;
    }

    public Grupos audioContentType(String audioContentType) {
        this.audioContentType = audioContentType;
        return this;
    }

    public void setAudioContentType(String audioContentType) {
        this.audioContentType = audioContentType;
    }

    public byte[] getVideo() {
        return video;
    }

    public Grupos video(byte[] video) {
        this.video = video;
        return this;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public String getVideoContentType() {
        return videoContentType;
    }

    public Grupos videoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
        return this;
    }

    public void setVideoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
    }

    public byte[] getImagen1() {
        return imagen1;
    }

    public Grupos imagen1(byte[] imagen1) {
        this.imagen1 = imagen1;
        return this;
    }

    public void setImagen1(byte[] imagen1) {
        this.imagen1 = imagen1;
    }

    public String getImagen1ContentType() {
        return imagen1ContentType;
    }

    public Grupos imagen1ContentType(String imagen1ContentType) {
        this.imagen1ContentType = imagen1ContentType;
        return this;
    }

    public void setImagen1ContentType(String imagen1ContentType) {
        this.imagen1ContentType = imagen1ContentType;
    }

    public byte[] getImagen2() {
        return imagen2;
    }

    public Grupos imagen2(byte[] imagen2) {
        this.imagen2 = imagen2;
        return this;
    }

    public void setImagen2(byte[] imagen2) {
        this.imagen2 = imagen2;
    }

    public String getImagen2ContentType() {
        return imagen2ContentType;
    }

    public Grupos imagen2ContentType(String imagen2ContentType) {
        this.imagen2ContentType = imagen2ContentType;
        return this;
    }

    public void setImagen2ContentType(String imagen2ContentType) {
        this.imagen2ContentType = imagen2ContentType;
    }

    public byte[] getBanner() {
        return banner;
    }

    public Grupos banner(byte[] banner) {
        this.banner = banner;
        return this;
    }

    public void setBanner(byte[] banner) {
        this.banner = banner;
    }

    public String getBannerContentType() {
        return bannerContentType;
    }

    public Grupos bannerContentType(String bannerContentType) {
        this.bannerContentType = bannerContentType;
        return this;
    }

    public void setBannerContentType(String bannerContentType) {
        this.bannerContentType = bannerContentType;
    }

    public CategoriaContenidos getCategoriaGrupo() {
        return categoriaGrupo;
    }

    public Grupos categoriaGrupo(CategoriaContenidos categoriaContenidos) {
        this.categoriaGrupo = categoriaContenidos;
        return this;
    }

    public void setCategoriaGrupo(CategoriaContenidos categoriaContenidos) {
        this.categoriaGrupo = categoriaContenidos;
    }

    public Evento getEvento() {
        return evento;
    }

    public Grupos evento(Evento evento) {
        this.evento = evento;
        return this;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grupos)) {
            return false;
        }
        return id != null && id.equals(((Grupos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Grupos{" +
            "id=" + getId() +
            ", nombreGrupo='" + getNombreGrupo() + "'" +
            ", instagram='" + getInstagram() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", twiter='" + getTwiter() + "'" +
            ", linkedIn='" + getLinkedIn() + "'" +
            ", tipoGrupo='" + getTipoGrupo() + "'" +
            ", tipoConsumo='" + getTipoConsumo() + "'" +
            ", valorSuscripcion=" + getValorSuscripcion() +
            ", impuesto='" + getImpuesto() + "'" +
            ", reglasGrupo='" + getReglasGrupo() + "'" +
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
            "}";
    }
}
