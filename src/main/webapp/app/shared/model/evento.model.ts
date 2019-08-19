import { ICategoriaContenidos } from 'app/shared/model/categoria-contenidos.model';
import { IGrupos } from 'app/shared/model/grupos.model';
import { IMiembros } from 'app/shared/model/miembros.model';

export const enum TipoConsumod {
  GRATIS = 'GRATIS',
  PAGO = 'PAGO'
}

export const enum Impuestod {
  IVA19 = 'IVA19',
  IVA6 = 'IVA6',
  IVA0 = 'IVA0',
  ICO = 'ICO',
  IPOCONSUMO8 = 'IPOCONSUMO8'
}

export const enum Categoriad {
  GENERAL = 'GENERAL',
  DE_GRUPO = 'DE_GRUPO',
  CORPORATIVO = 'CORPORATIVO',
  INSTITUCIONAL = 'INSTITUCIONAL',
  INTERNO = 'INTERNO'
}

export interface IEvento {
  id?: number;
  nombreEvento?: string;
  descripcion?: string;
  contenido?: any;
  audioContentType?: string;
  audio?: any;
  videoContentType?: string;
  video?: any;
  imagen1ContentType?: string;
  imagen1?: any;
  imagen2ContentType?: string;
  imagen2?: any;
  bannerContentType?: string;
  banner?: any;
  tipoConsumo?: TipoConsumod;
  valor?: number;
  impuesto?: Impuestod;
  tipoEvento?: Categoriad;
  categoriaEvento?: ICategoriaContenidos;
  grupos?: IGrupos;
  miembro?: IMiembros;
}

export class Evento implements IEvento {
  constructor(
    public id?: number,
    public nombreEvento?: string,
    public descripcion?: string,
    public contenido?: any,
    public audioContentType?: string,
    public audio?: any,
    public videoContentType?: string,
    public video?: any,
    public imagen1ContentType?: string,
    public imagen1?: any,
    public imagen2ContentType?: string,
    public imagen2?: any,
    public bannerContentType?: string,
    public banner?: any,
    public tipoConsumo?: TipoConsumod,
    public valor?: number,
    public impuesto?: Impuestod,
    public tipoEvento?: Categoriad,
    public categoriaEvento?: ICategoriaContenidos,
    public grupos?: IGrupos,
    public miembro?: IMiembros
  ) {}
}
