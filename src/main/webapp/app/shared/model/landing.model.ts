import { ISedes } from 'app/shared/model/sedes.model';

export const enum Impuestod {
  IVA19 = 'IVA19',
  IVA6 = 'IVA6',
  IVA0 = 'IVA0',
  ICO = 'ICO',
  IPOCONSUMO8 = 'IPOCONSUMO8'
}

export interface ILanding {
  id?: number;
  nombre?: string;
  descripcion?: string;
  facilidades?: string;
  telefonoNegocio?: string;
  numeroPuestos?: number;
  tarifaMensual?: number;
  impuesto?: Impuestod;
  imagen1ContentType?: string;
  imagen1?: any;
  imagen2ContentType?: string;
  imagen2?: any;
  imagen3ContentType?: string;
  imagen3?: any;
  sede?: ISedes;
}

export class Landing implements ILanding {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public facilidades?: string,
    public telefonoNegocio?: string,
    public numeroPuestos?: number,
    public tarifaMensual?: number,
    public impuesto?: Impuestod,
    public imagen1ContentType?: string,
    public imagen1?: any,
    public imagen2ContentType?: string,
    public imagen2?: any,
    public imagen3ContentType?: string,
    public imagen3?: any,
    public sede?: ISedes
  ) {}
}
