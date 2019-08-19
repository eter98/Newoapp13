import { Moment } from 'moment';
import { ITipoRegistroCompra } from 'app/shared/model/tipo-registro-compra.model';
import { IMiembros } from 'app/shared/model/miembros.model';

export interface IRegistroFacturacion {
  id?: number;
  valor?: number;
  fechaRegistro?: Moment;
  fechaFacturacion?: Moment;
  tipoRegistro?: ITipoRegistroCompra;
  miembro?: IMiembros;
}

export class RegistroFacturacion implements IRegistroFacturacion {
  constructor(
    public id?: number,
    public valor?: number,
    public fechaRegistro?: Moment,
    public fechaFacturacion?: Moment,
    public tipoRegistro?: ITipoRegistroCompra,
    public miembro?: IMiembros
  ) {}
}
