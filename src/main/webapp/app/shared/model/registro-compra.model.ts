import { IMiembros } from 'app/shared/model/miembros.model';

export interface IRegistroCompra {
  id?: number;
  consumoMarket?: boolean;
  valor?: number;
  miembro?: IMiembros;
}

export class RegistroCompra implements IRegistroCompra {
  constructor(public id?: number, public consumoMarket?: boolean, public valor?: number, public miembro?: IMiembros) {
    this.consumoMarket = this.consumoMarket || false;
  }
}
