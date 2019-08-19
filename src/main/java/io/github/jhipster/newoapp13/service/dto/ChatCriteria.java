package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.Chat} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.ChatResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /chats?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChatCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter mensaje;

    private IntegerFilter sender;

    private BooleanFilter read;

    private BooleanFilter delivered;

    private BooleanFilter sent;

    private ZonedDateTimeFilter fecha;

    private LongFilter chatsListadoId;

    private LongFilter deId;

    private LongFilter paraId;

    public ChatCriteria(){
    }

    public ChatCriteria(ChatCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.mensaje = other.mensaje == null ? null : other.mensaje.copy();
        this.sender = other.sender == null ? null : other.sender.copy();
        this.read = other.read == null ? null : other.read.copy();
        this.delivered = other.delivered == null ? null : other.delivered.copy();
        this.sent = other.sent == null ? null : other.sent.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.chatsListadoId = other.chatsListadoId == null ? null : other.chatsListadoId.copy();
        this.deId = other.deId == null ? null : other.deId.copy();
        this.paraId = other.paraId == null ? null : other.paraId.copy();
    }

    @Override
    public ChatCriteria copy() {
        return new ChatCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMensaje() {
        return mensaje;
    }

    public void setMensaje(StringFilter mensaje) {
        this.mensaje = mensaje;
    }

    public IntegerFilter getSender() {
        return sender;
    }

    public void setSender(IntegerFilter sender) {
        this.sender = sender;
    }

    public BooleanFilter getRead() {
        return read;
    }

    public void setRead(BooleanFilter read) {
        this.read = read;
    }

    public BooleanFilter getDelivered() {
        return delivered;
    }

    public void setDelivered(BooleanFilter delivered) {
        this.delivered = delivered;
    }

    public BooleanFilter getSent() {
        return sent;
    }

    public void setSent(BooleanFilter sent) {
        this.sent = sent;
    }

    public ZonedDateTimeFilter getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTimeFilter fecha) {
        this.fecha = fecha;
    }

    public LongFilter getChatsListadoId() {
        return chatsListadoId;
    }

    public void setChatsListadoId(LongFilter chatsListadoId) {
        this.chatsListadoId = chatsListadoId;
    }

    public LongFilter getDeId() {
        return deId;
    }

    public void setDeId(LongFilter deId) {
        this.deId = deId;
    }

    public LongFilter getParaId() {
        return paraId;
    }

    public void setParaId(LongFilter paraId) {
        this.paraId = paraId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChatCriteria that = (ChatCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(mensaje, that.mensaje) &&
            Objects.equals(sender, that.sender) &&
            Objects.equals(read, that.read) &&
            Objects.equals(delivered, that.delivered) &&
            Objects.equals(sent, that.sent) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(chatsListadoId, that.chatsListadoId) &&
            Objects.equals(deId, that.deId) &&
            Objects.equals(paraId, that.paraId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        mensaje,
        sender,
        read,
        delivered,
        sent,
        fecha,
        chatsListadoId,
        deId,
        paraId
        );
    }

    @Override
    public String toString() {
        return "ChatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (mensaje != null ? "mensaje=" + mensaje + ", " : "") +
                (sender != null ? "sender=" + sender + ", " : "") +
                (read != null ? "read=" + read + ", " : "") +
                (delivered != null ? "delivered=" + delivered + ", " : "") +
                (sent != null ? "sent=" + sent + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (chatsListadoId != null ? "chatsListadoId=" + chatsListadoId + ", " : "") +
                (deId != null ? "deId=" + deId + ", " : "") +
                (paraId != null ? "paraId=" + paraId + ", " : "") +
            "}";
    }

}
