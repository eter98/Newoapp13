package io.github.jhipster.newoapp13.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.newoapp13.domain.enumeration.Estatusd;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link io.github.jhipster.newoapp13.domain.ChatsListado} entity. This class is used
 * in {@link io.github.jhipster.newoapp13.web.rest.ChatsListadoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /chats-listados?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChatsListadoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Estatusd
     */
    public static class EstatusdFilter extends Filter<Estatusd> {

        public EstatusdFilter() {
        }

        public EstatusdFilter(EstatusdFilter filter) {
            super(filter);
        }

        @Override
        public EstatusdFilter copy() {
            return new EstatusdFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter descripcion;

    private EstatusdFilter estatus;

    private IntegerFilter count;

    private IntegerFilter badge;

    private StringFilter time;

    private ZonedDateTimeFilter sendTime;

    private BooleanFilter grupo;

    private LongFilter propietarioId;

    private LongFilter destinatarioId;

    private LongFilter chatId;

    public ChatsListadoCriteria(){
    }

    public ChatsListadoCriteria(ChatsListadoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.estatus = other.estatus == null ? null : other.estatus.copy();
        this.count = other.count == null ? null : other.count.copy();
        this.badge = other.badge == null ? null : other.badge.copy();
        this.time = other.time == null ? null : other.time.copy();
        this.sendTime = other.sendTime == null ? null : other.sendTime.copy();
        this.grupo = other.grupo == null ? null : other.grupo.copy();
        this.propietarioId = other.propietarioId == null ? null : other.propietarioId.copy();
        this.destinatarioId = other.destinatarioId == null ? null : other.destinatarioId.copy();
        this.chatId = other.chatId == null ? null : other.chatId.copy();
    }

    @Override
    public ChatsListadoCriteria copy() {
        return new ChatsListadoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public EstatusdFilter getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusdFilter estatus) {
        this.estatus = estatus;
    }

    public IntegerFilter getCount() {
        return count;
    }

    public void setCount(IntegerFilter count) {
        this.count = count;
    }

    public IntegerFilter getBadge() {
        return badge;
    }

    public void setBadge(IntegerFilter badge) {
        this.badge = badge;
    }

    public StringFilter getTime() {
        return time;
    }

    public void setTime(StringFilter time) {
        this.time = time;
    }

    public ZonedDateTimeFilter getSendTime() {
        return sendTime;
    }

    public void setSendTime(ZonedDateTimeFilter sendTime) {
        this.sendTime = sendTime;
    }

    public BooleanFilter getGrupo() {
        return grupo;
    }

    public void setGrupo(BooleanFilter grupo) {
        this.grupo = grupo;
    }

    public LongFilter getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(LongFilter propietarioId) {
        this.propietarioId = propietarioId;
    }

    public LongFilter getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(LongFilter destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public LongFilter getChatId() {
        return chatId;
    }

    public void setChatId(LongFilter chatId) {
        this.chatId = chatId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChatsListadoCriteria that = (ChatsListadoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(estatus, that.estatus) &&
            Objects.equals(count, that.count) &&
            Objects.equals(badge, that.badge) &&
            Objects.equals(time, that.time) &&
            Objects.equals(sendTime, that.sendTime) &&
            Objects.equals(grupo, that.grupo) &&
            Objects.equals(propietarioId, that.propietarioId) &&
            Objects.equals(destinatarioId, that.destinatarioId) &&
            Objects.equals(chatId, that.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        descripcion,
        estatus,
        count,
        badge,
        time,
        sendTime,
        grupo,
        propietarioId,
        destinatarioId,
        chatId
        );
    }

    @Override
    public String toString() {
        return "ChatsListadoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (estatus != null ? "estatus=" + estatus + ", " : "") +
                (count != null ? "count=" + count + ", " : "") +
                (badge != null ? "badge=" + badge + ", " : "") +
                (time != null ? "time=" + time + ", " : "") +
                (sendTime != null ? "sendTime=" + sendTime + ", " : "") +
                (grupo != null ? "grupo=" + grupo + ", " : "") +
                (propietarioId != null ? "propietarioId=" + propietarioId + ", " : "") +
                (destinatarioId != null ? "destinatarioId=" + destinatarioId + ", " : "") +
                (chatId != null ? "chatId=" + chatId + ", " : "") +
            "}";
    }

}
