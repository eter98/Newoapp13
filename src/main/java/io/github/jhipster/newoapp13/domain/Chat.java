package io.github.jhipster.newoapp13.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Chat.
 */
@Entity
@Table(name = "chat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "chat")
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @Column(name = "sender")
    private Integer sender;

    @Column(name = "read")
    private Boolean read;

    @Column(name = "delivered")
    private Boolean delivered;

    @Column(name = "sent")
    private Boolean sent;

    @Column(name = "fecha")
    private ZonedDateTime fecha;

    @ManyToOne
    @JsonIgnoreProperties("chats")
    private ChatsListado chatsListado;

    @ManyToOne
    @JsonIgnoreProperties("chats")
    private Miembros de;

    @ManyToOne
    @JsonIgnoreProperties("chats")
    private Miembros para;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Chat mensaje(String mensaje) {
        this.mensaje = mensaje;
        return this;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getSender() {
        return sender;
    }

    public Chat sender(Integer sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Boolean isRead() {
        return read;
    }

    public Chat read(Boolean read) {
        this.read = read;
        return this;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean isDelivered() {
        return delivered;
    }

    public Chat delivered(Boolean delivered) {
        this.delivered = delivered;
        return this;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Boolean isSent() {
        return sent;
    }

    public Chat sent(Boolean sent) {
        this.sent = sent;
        return this;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public Chat fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public ChatsListado getChatsListado() {
        return chatsListado;
    }

    public Chat chatsListado(ChatsListado chatsListado) {
        this.chatsListado = chatsListado;
        return this;
    }

    public void setChatsListado(ChatsListado chatsListado) {
        this.chatsListado = chatsListado;
    }

    public Miembros getDe() {
        return de;
    }

    public Chat de(Miembros miembros) {
        this.de = miembros;
        return this;
    }

    public void setDe(Miembros miembros) {
        this.de = miembros;
    }

    public Miembros getPara() {
        return para;
    }

    public Chat para(Miembros miembros) {
        this.para = miembros;
        return this;
    }

    public void setPara(Miembros miembros) {
        this.para = miembros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chat)) {
            return false;
        }
        return id != null && id.equals(((Chat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Chat{" +
            "id=" + getId() +
            ", mensaje='" + getMensaje() + "'" +
            ", sender=" + getSender() +
            ", read='" + isRead() + "'" +
            ", delivered='" + isDelivered() + "'" +
            ", sent='" + isSent() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
