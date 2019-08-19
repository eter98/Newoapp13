import { Moment } from 'moment';
import { IMiembros } from 'app/shared/model/miembros.model';
import { IChat } from 'app/shared/model/chat.model';

export const enum Estatusd {
  EnLinea = 'EnLinea',
  Desconectado = 'Desconectado'
}

export interface IChatsListado {
  id?: number;
  descripcion?: string;
  estatus?: Estatusd;
  count?: number;
  badge?: number;
  time?: string;
  sendTime?: Moment;
  grupo?: boolean;
  propietario?: IMiembros;
  destinatario?: IMiembros;
  chats?: IChat[];
}

export class ChatsListado implements IChatsListado {
  constructor(
    public id?: number,
    public descripcion?: string,
    public estatus?: Estatusd,
    public count?: number,
    public badge?: number,
    public time?: string,
    public sendTime?: Moment,
    public grupo?: boolean,
    public propietario?: IMiembros,
    public destinatario?: IMiembros,
    public chats?: IChat[]
  ) {
    this.grupo = this.grupo || false;
  }
}
