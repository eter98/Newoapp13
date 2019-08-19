export interface ITipoRegistroCompra {
  id?: number;
  descripcion?: string;
}

export class TipoRegistroCompra implements ITipoRegistroCompra {
  constructor(public id?: number, public descripcion?: string) {}
}
