import { Moment } from 'moment';
import { IMiembros } from 'app/shared/model/miembros.model';
import { ITipoRegistroCompra } from 'app/shared/model/tipo-registro-compra.model';

export interface IRegistroCompra {
  id?: number;
  valor?: number;
  fechaRegistro?: Moment;
  idTransaccion?: number;
  miembro?: IMiembros;
  tipoRegistro?: ITipoRegistroCompra;
}

export class RegistroCompra implements IRegistroCompra {
  constructor(
    public id?: number,
    public valor?: number,
    public fechaRegistro?: Moment,
    public idTransaccion?: number,
    public miembro?: IMiembros,
    public tipoRegistro?: ITipoRegistroCompra
  ) {}
}
