import { IEmpresa } from 'app/shared/model/empresa.model';

export interface IEquipoEmpresas {
  id?: number;
  nombre?: string;
  telefono?: string;
  correo?: string;
  direccion?: string;
  descripcion?: any;
  logoContentType?: string;
  logo?: any;
  paginaWeb?: string;
  conocimientosQueDomina?: string;
  temasDeInteres?: string;
  empresa?: IEmpresa;
}

export class EquipoEmpresas implements IEquipoEmpresas {
  constructor(
    public id?: number,
    public nombre?: string,
    public telefono?: string,
    public correo?: string,
    public direccion?: string,
    public descripcion?: any,
    public logoContentType?: string,
    public logo?: any,
    public paginaWeb?: string,
    public conocimientosQueDomina?: string,
    public temasDeInteres?: string,
    public empresa?: IEmpresa
  ) {}
}
