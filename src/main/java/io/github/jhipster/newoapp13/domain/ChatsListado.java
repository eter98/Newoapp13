package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import io.github.jhipster.newoapp13.domain.enumeration.Estatusd;

/**
 * A ChatsListado.
 */
@Entity
@Table(name = "chats_listado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "chatslistado")
public class ChatsListado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus")
    private Estatusd estatus;

    @Column(name = "count")
    private Integer count;

    @Column(name = "badge")
    private Integer badge;

    @Column(name = "time")
    private String time;

    @Column(name = "send_time")
    private Instant sendTime;

    @Column(name = "grupo")
    private Boolean grupo;

    @ManyToOne
    @JsonIgnoreProperties("chatsListados")
    private Miembros propietario;

    @ManyToOne
    @JsonIgnoreProperties("chatsListados")
    private Miembros destinatario;

    @OneToMany(mappedBy = "chatsListado")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chat> chats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ChatsListado descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estatusd getEstatus() {
        return estatus;
    }

    public ChatsListado estatus(Estatusd estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(Estatusd estatus) {
        this.estatus = estatus;
    }

    public Integer getCount() {
        return count;
    }

    public ChatsListado count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBadge() {
        return badge;
    }

    public ChatsListado badge(Integer badge) {
        this.badge = badge;
        return this;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public String getTime() {
        return time;
    }

    public ChatsListado time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Instant getSendTime() {
        return sendTime;
    }

    public ChatsListado sendTime(Instant sendTime) {
        this.sendTime = sendTime;
        return this;
    }

    public void setSendTime(Instant sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean isGrupo() {
        return grupo;
    }

    public ChatsListado grupo(Boolean grupo) {
        this.grupo = grupo;
        return this;
    }

    public void setGrupo(Boolean grupo) {
        this.grupo = grupo;
    }

    public Miembros getPropietario() {
        return propietario;
    }

    public ChatsListado propietario(Miembros miembros) {
        this.propietario = miembros;
        return this;
    }

    public void setPropietario(Miembros miembros) {
        this.propietario = miembros;
    }

    public Miembros getDestinatario() {
        return destinatario;
    }

    public ChatsListado destinatario(Miembros miembros) {
        this.destinatario = miembros;
        return this;
    }

    public void setDestinatario(Miembros miembros) {
        this.destinatario = miembros;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public ChatsListado chats(Set<Chat> chats) {
        this.chats = chats;
        return this;
    }

    public ChatsListado addChat(Chat chat) {
        this.chats.add(chat);
        chat.setChatsListado(this);
        return this;
    }

    public ChatsListado removeChat(Chat chat) {
        this.chats.remove(chat);
        chat.setChatsListado(null);
        return this;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatsListado)) {
            return false;
        }
        return id != null && id.equals(((ChatsListado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ChatsListado{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", count=" + getCount() +
            ", badge=" + getBadge() +
            ", time='" + getTime() + "'" +
            ", sendTime='" + getSendTime() + "'" +
            ", grupo='" + isGrupo() + "'" +
            "}";
    }
}
