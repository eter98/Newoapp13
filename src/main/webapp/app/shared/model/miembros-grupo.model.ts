import { IMiembros } from 'app/shared/model/miembros.model';
import { IGrupos } from 'app/shared/model/grupos.model';

export interface IMiembrosGrupo {
  id?: number;
  miembro?: IMiembros;
  grupo?: IGrupos;
}

export class MiembrosGrupo implements IMiembrosGrupo {
  constructor(public id?: number, public miembro?: IMiembros, public grupo?: IGrupos) {}
}
