package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A PerfilEquipoEmpresa.
 */
@Entity
@Table(name = "perfil_equipo_empresa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "perfilequipoempresa")
public class PerfilEquipoEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "biografia", nullable = false)
    private String biografia;

    @Lob
    @Column(name = "foto_1")
    private byte[] foto1;

    @Column(name = "foto_1_content_type")
    private String foto1ContentType;

    @Lob
    @Column(name = "foto_2")
    private byte[] foto2;

    @Column(name = "foto_2_content_type")
    private String foto2ContentType;

    @Lob
    @Column(name = "fot_3")
    private byte[] fot3;

    @Column(name = "fot_3_content_type")
    private String fot3ContentType;

    @NotNull
    @Column(name = "conocimientos_que_domina", nullable = false)
    private String conocimientosQueDomina;

    @NotNull
    @Column(name = "temas_de_interes", nullable = false)
    private String temasDeInteres;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "id_google")
    private String idGoogle;

    @Column(name = "twiter")
    private String twiter;

    @ManyToOne
    @JsonIgnoreProperties("perfilEquipoEmpresas")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBiografia() {
        return biografia;
    }

    public PerfilEquipoEmpresa biografia(String biografia) {
        this.biografia = biografia;
        return this;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public byte[] getFoto1() {
        return foto1;
    }

    public PerfilEquipoEmpresa foto1(byte[] foto1) {
        this.foto1 = foto1;
        return this;
    }

    public void setFoto1(byte[] foto1) {
        this.foto1 = foto1;
    }

    public String getFoto1ContentType() {
        return foto1ContentType;
    }

    public PerfilEquipoEmpresa foto1ContentType(String foto1ContentType) {
        this.foto1ContentType = foto1ContentType;
        return this;
    }

    public void setFoto1ContentType(String foto1ContentType) {
        this.foto1ContentType = foto1ContentType;
    }

    public byte[] getFoto2() {
        return foto2;
    }

    public PerfilEquipoEmpresa foto2(byte[] foto2) {
        this.foto2 = foto2;
        return this;
    }

    public void setFoto2(byte[] foto2) {
        this.foto2 = foto2;
    }

    public String getFoto2ContentType() {
        return foto2ContentType;
    }

    public PerfilEquipoEmpresa foto2ContentType(String foto2ContentType) {
        this.foto2ContentType = foto2ContentType;
        return this;
    }

    public void setFoto2ContentType(String foto2ContentType) {
        this.foto2ContentType = foto2ContentType;
    }

    public byte[] getFot3() {
        return fot3;
    }

    public PerfilEquipoEmpresa fot3(byte[] fot3) {
        this.fot3 = fot3;
        return this;
    }

    public void setFot3(byte[] fot3) {
        this.fot3 = fot3;
    }

    public String getFot3ContentType() {
        return fot3ContentType;
    }

    public PerfilEquipoEmpresa fot3ContentType(String fot3ContentType) {
        this.fot3ContentType = fot3ContentType;
        return this;
    }

    public void setFot3ContentType(String fot3ContentType) {
        this.fot3ContentType = fot3ContentType;
    }

    public String getConocimientosQueDomina() {
        return conocimientosQueDomina;
    }

    public PerfilEquipoEmpresa conocimientosQueDomina(String conocimientosQueDomina) {
        this.conocimientosQueDomina = conocimientosQueDomina;
        return this;
    }

    public void setConocimientosQueDomina(String conocimientosQueDomina) {
        this.conocimientosQueDomina = conocimientosQueDomina;
    }

    public String getTemasDeInteres() {
        return temasDeInteres;
    }

    public PerfilEquipoEmpresa temasDeInteres(String temasDeInteres) {
        this.temasDeInteres = temasDeInteres;
        return this;
    }

    public void setTemasDeInteres(String temasDeInteres) {
        this.temasDeInteres = temasDeInteres;
    }

    public String getFacebook() {
        return facebook;
    }

    public PerfilEquipoEmpresa facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public PerfilEquipoEmpresa instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public PerfilEquipoEmpresa idGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
        return this;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

    public String getTwiter() {
        return twiter;
    }

    public PerfilEquipoEmpresa twiter(String twiter) {
        this.twiter = twiter;
        return this;
    }

    public void setTwiter(String twiter) {
        this.twiter = twiter;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public PerfilEquipoEmpresa empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfilEquipoEmpresa)) {
            return false;
        }
        return id != null && id.equals(((PerfilEquipoEmpresa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PerfilEquipoEmpresa{" +
            "id=" + getId() +
            ", biografia='" + getBiografia() + "'" +
            ", foto1='" + getFoto1() + "'" +
            ", foto1ContentType='" + getFoto1ContentType() + "'" +
            ", foto2='" + getFoto2() + "'" +
            ", foto2ContentType='" + getFoto2ContentType() + "'" +
            ", fot3='" + getFot3() + "'" +
            ", fot3ContentType='" + getFot3ContentType() + "'" +
            ", conocimientosQueDomina='" + getConocimientosQueDomina() + "'" +
            ", temasDeInteres='" + getTemasDeInteres() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", instagram='" + getInstagram() + "'" +
            ", idGoogle='" + getIdGoogle() + "'" +
            ", twiter='" + getTwiter() + "'" +
            "}";
    }
}
