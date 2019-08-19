import { IEmpresa } from 'app/shared/model/empresa.model';

export interface IPerfilEquipoEmpresa {
  id?: number;
  biografia?: any;
  foto1ContentType?: string;
  foto1?: any;
  foto2ContentType?: string;
  foto2?: any;
  fot3ContentType?: string;
  fot3?: any;
  conocimientosQueDomina?: string;
  temasDeInteres?: string;
  facebook?: string;
  instagram?: string;
  idGoogle?: string;
  twiter?: string;
  empresa?: IEmpresa;
}

export class PerfilEquipoEmpresa implements IPerfilEquipoEmpresa {
  constructor(
    public id?: number,
    public biografia?: any,
    public foto1ContentType?: string,
    public foto1?: any,
    public foto2ContentType?: string,
    public foto2?: any,
    public fot3ContentType?: string,
    public fot3?: any,
    public conocimientosQueDomina?: string,
    public temasDeInteres?: string,
    public facebook?: string,
    public instagram?: string,
    public idGoogle?: string,
    public twiter?: string,
    public empresa?: IEmpresa
  ) {}
}
