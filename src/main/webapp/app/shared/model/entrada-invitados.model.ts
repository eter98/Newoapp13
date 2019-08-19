import { Moment } from 'moment';
import { IEspacioLibre } from 'app/shared/model/espacio-libre.model';
import { ILanding } from 'app/shared/model/landing.model';
import { IEspaciosReserva } from 'app/shared/model/espacios-reserva.model';
import { IInvitados } from 'app/shared/model/invitados.model';

export const enum TipoEntradad {
  INGRESO = 'INGRESO',
  SALIDA = 'SALIDA'
}

export const enum TipoIngresod {
  Espacio_Libre = 'Espacio_Libre',
  Reserva = 'Reserva',
  Oficina = 'Oficina'
}

export interface IEntradaInvitados {
  id?: number;
  registroFecha?: Moment;
  tipoEntrada?: TipoEntradad;
  tipoIngreso?: TipoIngresod;
  tiempoMaximo?: boolean;
  espacio?: IEspacioLibre;
  oficina?: ILanding;
  espacioReserva?: IEspaciosReserva;
  invitado?: IInvitados;
}

export class EntradaInvitados implements IEntradaInvitados {
  constructor(
    public id?: number,
    public registroFecha?: Moment,
    public tipoEntrada?: TipoEntradad,
    public tipoIngreso?: TipoIngresod,
    public tiempoMaximo?: boolean,
    public espacio?: IEspacioLibre,
    public oficina?: ILanding,
    public espacioReserva?: IEspaciosReserva,
    public invitado?: IInvitados
  ) {
    this.tiempoMaximo = this.tiempoMaximo || false;
  }
}
