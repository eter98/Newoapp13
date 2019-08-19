import { ICategoriaContenidos } from 'app/shared/model/categoria-contenidos.model';
import { IEvento } from 'app/shared/model/evento.model';

export const enum TipoGrupod {
  INTERNO = 'INTERNO',
  EXTERNO = 'EXTERNO',
  PATROCINADO = 'PATROCINADO',
  PUBLICO = 'PUBLICO'
}

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

export interface IGrupos {
  id?: number;
  nombreGrupo?: string;
  instagram?: string;
  facebook?: string;
  twiter?: string;
  linkedIn?: string;
  tipoGrupo?: TipoGrupod;
  tipoConsumo?: TipoConsumod;
  valorSuscripcion?: number;
  impuesto?: Impuestod;
  reglasGrupo?: any;
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
  categoriaGrupo?: ICategoriaContenidos;
  evento?: IEvento;
}

export class Grupos implements IGrupos {
  constructor(
    public id?: number,
    public nombreGrupo?: string,
    public instagram?: string,
    public facebook?: string,
    public twiter?: string,
    public linkedIn?: string,
    public tipoGrupo?: TipoGrupod,
    public tipoConsumo?: TipoConsumod,
    public valorSuscripcion?: number,
    public impuesto?: Impuestod,
    public reglasGrupo?: any,
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
    public categoriaGrupo?: ICategoriaContenidos,
    public evento?: IEvento
  ) {}
}
