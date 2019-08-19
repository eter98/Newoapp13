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
import java.time.LocalDate;

import io.github.jhipster.newoapp13.domain.enumeration.TipoDocumentod;

import io.github.jhipster.newoapp13.domain.enumeration.Generod;

/**
 * A Miembros.
 */
@Entity
@Table(name = "miembros")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "miembros")
public class Miembros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumentod tipoDocumento;

    @NotNull
    @Column(name = "identificacion", nullable = false)
    private Integer identificacion;

    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_registro")
    private Instant fechaRegistro;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private Generod genero;

    @NotNull
    @Size(min = 11, max = 13)
    @Column(name = "celular", length = 13, nullable = false)
    private String celular;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "biografia")
    private String biografia;

    
    @Lob
    @Column(name = "foto_1", nullable = false)
    private byte[] foto1;

    @Column(name = "foto_1_content_type", nullable = false)
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

    @Column(name = "derechos_de_compra")
    private Boolean derechosDeCompra;

    @Column(name = "acceso_ilimitado")
    private Boolean accesoIlimitado;

    @Column(name = "aliado")
    private Boolean aliado;

    @Column(name = "host")
    private Boolean host;

    @OneToOne
    @JoinColumn(unique = true)
    private User miembro;

    @ManyToOne
    @JsonIgnoreProperties("miembros")
    private Pais nacionalidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDocumentod getTipoDocumento() {
        return tipoDocumento;
    }

    public Miembros tipoDocumento(TipoDocumentod tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return this;
    }

    public void setTipoDocumento(TipoDocumentod tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getIdentificacion() {
        return identificacion;
    }

    public Miembros identificacion(Integer identificacion) {
        this.identificacion = identificacion;
        return this;
    }

    public void setIdentificacion(Integer identificacion) {
        this.identificacion = identificacion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Miembros fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Instant getFechaRegistro() {
        return fechaRegistro;
    }

    public Miembros fechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
        return this;
    }

    public void setFechaRegistro(Instant fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Generod getGenero() {
        return genero;
    }

    public Miembros genero(Generod genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(Generod genero) {
        this.genero = genero;
    }

    public String getCelular() {
        return celular;
    }

    public Miembros celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getBiografia() {
        return biografia;
    }

    public Miembros biografia(String biografia) {
        this.biografia = biografia;
        return this;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public byte[] getFoto1() {
        return foto1;
    }

    public Miembros foto1(byte[] foto1) {
        this.foto1 = foto1;
        return this;
    }

    public void setFoto1(byte[] foto1) {
        this.foto1 = foto1;
    }

    public String getFoto1ContentType() {
        return foto1ContentType;
    }

    public Miembros foto1ContentType(String foto1ContentType) {
        this.foto1ContentType = foto1ContentType;
        return this;
    }

    public void setFoto1ContentType(String foto1ContentType) {
        this.foto1ContentType = foto1ContentType;
    }

    public byte[] getFoto2() {
        return foto2;
    }

    public Miembros foto2(byte[] foto2) {
        this.foto2 = foto2;
        return this;
    }

    public void setFoto2(byte[] foto2) {
        this.foto2 = foto2;
    }

    public String getFoto2ContentType() {
        return foto2ContentType;
    }

    public Miembros foto2ContentType(String foto2ContentType) {
        this.foto2ContentType = foto2ContentType;
        return this;
    }

    public void setFoto2ContentType(String foto2ContentType) {
        this.foto2ContentType = foto2ContentType;
    }

    public byte[] getFot3() {
        return fot3;
    }

    public Miembros fot3(byte[] fot3) {
        this.fot3 = fot3;
        return this;
    }

    public void setFot3(byte[] fot3) {
        this.fot3 = fot3;
    }

    public String getFot3ContentType() {
        return fot3ContentType;
    }

    public Miembros fot3ContentType(String fot3ContentType) {
        this.fot3ContentType = fot3ContentType;
        return this;
    }

    public void setFot3ContentType(String fot3ContentType) {
        this.fot3ContentType = fot3ContentType;
    }

    public String getConocimientosQueDomina() {
        return conocimientosQueDomina;
    }

    public Miembros conocimientosQueDomina(String conocimientosQueDomina) {
        this.conocimientosQueDomina = conocimientosQueDomina;
        return this;
    }

    public void setConocimientosQueDomina(String conocimientosQueDomina) {
        this.conocimientosQueDomina = conocimientosQueDomina;
    }

    public String getTemasDeInteres() {
        return temasDeInteres;
    }

    public Miembros temasDeInteres(String temasDeInteres) {
        this.temasDeInteres = temasDeInteres;
        return this;
    }

    public void setTemasDeInteres(String temasDeInteres) {
        this.temasDeInteres = temasDeInteres;
    }

    public String getFacebook() {
        return facebook;
    }

    public Miembros facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public Miembros instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public Miembros idGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
        return this;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

    public String getTwiter() {
        return twiter;
    }

    public Miembros twiter(String twiter) {
        this.twiter = twiter;
        return this;
    }

    public void setTwiter(String twiter) {
        this.twiter = twiter;
    }

    public Boolean isDerechosDeCompra() {
        return derechosDeCompra;
    }

    public Miembros derechosDeCompra(Boolean derechosDeCompra) {
        this.derechosDeCompra = derechosDeCompra;
        return this;
    }

    public void setDerechosDeCompra(Boolean derechosDeCompra) {
        this.derechosDeCompra = derechosDeCompra;
    }

    public Boolean isAccesoIlimitado() {
        return accesoIlimitado;
    }

    public Miembros accesoIlimitado(Boolean accesoIlimitado) {
        this.accesoIlimitado = accesoIlimitado;
        return this;
    }

    public void setAccesoIlimitado(Boolean accesoIlimitado) {
        this.accesoIlimitado = accesoIlimitado;
    }

    public Boolean isAliado() {
        return aliado;
    }

    public Miembros aliado(Boolean aliado) {
        this.aliado = aliado;
        return this;
    }

    public void setAliado(Boolean aliado) {
        this.aliado = aliado;
    }

    public Boolean isHost() {
        return host;
    }

    public Miembros host(Boolean host) {
        this.host = host;
        return this;
    }

    public void setHost(Boolean host) {
        this.host = host;
    }

    public User getMiembro() {
        return miembro;
    }

    public Miembros miembro(User user) {
        this.miembro = user;
        return this;
    }

    public void setMiembro(User user) {
        this.miembro = user;
    }

    public Pais getNacionalidad() {
        return nacionalidad;
    }

    public Miembros nacionalidad(Pais pais) {
        this.nacionalidad = pais;
        return this;
    }

    public void setNacionalidad(Pais pais) {
        this.nacionalidad = pais;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Miembros)) {
            return false;
        }
        return id != null && id.equals(((Miembros) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Miembros{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            ", identificacion=" + getIdentificacion() +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", genero='" + getGenero() + "'" +
            ", celular='" + getCelular() + "'" +
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
            ", derechosDeCompra='" + isDerechosDeCompra() + "'" +
            ", accesoIlimitado='" + isAccesoIlimitado() + "'" +
            ", aliado='" + isAliado() + "'" +
            ", host='" + isHost() + "'" +
            "}";
    }
}
