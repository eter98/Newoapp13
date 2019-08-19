import { Moment } from 'moment';
import { IMiembros } from 'app/shared/model/miembros.model';
import { IEspaciosReserva } from 'app/shared/model/espacios-reserva.model';

export const enum EstadoReservad {
  Cancelada = 'Cancelada',
  Activa = 'Activa',
  Reservada = 'Reservada',
  Ocupada = 'Ocupada',
  Extendida = 'Extendida',
  Cerrada = 'Cerrada'
}

export interface IReservas {
  id?: number;
  registroFechaEntrada?: Moment;
  registroFechaSalida?: Moment;
  estadoReserva?: EstadoReservad;
  miembro?: IMiembros;
  espacio?: IEspaciosReserva;
}

export class Reservas implements IReservas {
  constructor(
    public id?: number,
    public registroFechaEntrada?: Moment,
    public registroFechaSalida?: Moment,
    public estadoReserva?: EstadoReservad,
    public miembro?: IMiembros,
    public espacio?: IEspaciosReserva
  ) {}
}
