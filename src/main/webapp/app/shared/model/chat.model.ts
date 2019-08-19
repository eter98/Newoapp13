import { Moment } from 'moment';
import { IChatsListado } from 'app/shared/model/chats-listado.model';
import { IMiembros } from 'app/shared/model/miembros.model';

export interface IChat {
  id?: number;
  mensaje?: string;
  sender?: number;
  read?: boolean;
  delivered?: boolean;
  sent?: boolean;
  fecha?: Moment;
  chatsListado?: IChatsListado;
  de?: IMiembros;
  para?: IMiembros;
}

export class Chat implements IChat {
  constructor(
    public id?: number,
    public mensaje?: string,
    public sender?: number,
    public read?: boolean,
    public delivered?: boolean,
    public sent?: boolean,
    public fecha?: Moment,
    public chatsListado?: IChatsListado,
    public de?: IMiembros,
    public para?: IMiembros
  ) {
    this.read = this.read || false;
    this.delivered = this.delivered || false;
    this.sent = this.sent || false;
  }
}
