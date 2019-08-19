import { IEquipoEmpresas } from 'app/shared/model/equipo-empresas.model';
import { IMiembros } from 'app/shared/model/miembros.model';

export interface IMiembrosEquipoEmpresas {
  id?: number;
  equipo?: IEquipoEmpresas;
  miembro?: IMiembros;
}

export class MiembrosEquipoEmpresas implements IMiembrosEquipoEmpresas {
  constructor(public id?: number, public equipo?: IEquipoEmpresas, public miembro?: IMiembros) {}
}
