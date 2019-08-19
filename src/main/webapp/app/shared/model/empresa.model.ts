import { IMiembros } from 'app/shared/model/miembros.model';

export interface IEmpresa {
  id?: number;
  razonSocial?: string;
  nit?: string;
  direccion?: string;
  telefono?: string;
  correo?: string;
  web?: string;
  celular?: string;
  biografia?: any;
  imagen1ContentType?: string;
  imagen1?: any;
  imagen2ContentType?: string;
  imagen2?: any;
  imagen3ContentType?: string;
  imagen3?: any;
  facebook?: string;
  instagram?: string;
  idGoogle?: string;
  twiter?: string;
  conocimientosQueDomina?: string;
  temasDeInteres?: string;
  miembro?: IMiembros;
}

export class Empresa implements IEmpresa {
  constructor(
    public id?: number,
    public razonSocial?: string,
    public nit?: string,
    public direccion?: string,
    public telefono?: string,
    public correo?: string,
    public web?: string,
    public celular?: string,
    public biografia?: any,
    public imagen1ContentType?: string,
    public imagen1?: any,
    public imagen2ContentType?: string,
    public imagen2?: any,
    public imagen3ContentType?: string,
    public imagen3?: any,
    public facebook?: string,
    public instagram?: string,
    public idGoogle?: string,
    public twiter?: string,
    public conocimientosQueDomina?: string,
    public temasDeInteres?: string,
    public miembro?: IMiembros
  ) {}
}
