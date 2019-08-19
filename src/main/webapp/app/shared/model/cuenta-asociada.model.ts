import { Moment } from 'moment';

export interface ICuentaAsociada {
  id?: number;
  identificaciontitular?: string;
  nombreTitular?: string;
  apellidoTitular?: string;
  numeroCuenta?: string;
  tipoCuenta?: string;
  codigoSeguridad?: string;
  fechaVencimiento?: Moment;
}

export class CuentaAsociada implements ICuentaAsociada {
  constructor(
    public id?: number,
    public identificaciontitular?: string,
    public nombreTitular?: string,
    public apellidoTitular?: string,
    public numeroCuenta?: string,
    public tipoCuenta?: string,
    public codigoSeguridad?: string,
    public fechaVencimiento?: Moment
  ) {}
}
