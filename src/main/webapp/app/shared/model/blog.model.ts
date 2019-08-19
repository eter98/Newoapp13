import { Moment } from 'moment';
import { IMiembros } from 'app/shared/model/miembros.model';
import { ICategoriaContenidos } from 'app/shared/model/categoria-contenidos.model';
import { IGrupos } from 'app/shared/model/grupos.model';

export const enum Categoriad {
  GENERAL = 'GENERAL',
  DE_GRUPO = 'DE_GRUPO',
  CORPORATIVO = 'CORPORATIVO',
  INSTITUCIONAL = 'INSTITUCIONAL',
  INTERNO = 'INTERNO'
}

export const enum EstadoPublicaciond {
  BORRADOR = 'BORRADOR',
  EN_REVISION = 'EN_REVISION',
  APROBADO = 'APROBADO',
  PUBLICADO = 'PUBLICADO',
  RECHAZADO = 'RECHAZADO'
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

export interface IBlog {
  id?: number;
  titulo?: string;
  descripcion?: string;
  tipoContenido?: Categoriad;
  contenido?: any;
  fecha?: Moment;
  audioContentType?: string;
  audio?: any;
  video?: string;
  imagen1ContentType?: string;
  imagen1?: any;
  imagen2ContentType?: string;
  imagen2?: any;
  bannerContentType?: string;
  banner?: any;
  estadoPublicacion?: EstadoPublicaciond;
  tipoConsumo?: TipoConsumod;
  valor?: number;
  impuesto?: Impuestod;
  miembro?: IMiembros;
  categoriaBlog?: ICategoriaContenidos;
  grupos?: IGrupos;
}

export class Blog implements IBlog {
  constructor(
    public id?: number,
    public titulo?: string,
    public descripcion?: string,
    public tipoContenido?: Categoriad,
    public contenido?: any,
    public fecha?: Moment,
    public audioContentType?: string,
    public audio?: any,
    public video?: string,
    public imagen1ContentType?: string,
    public imagen1?: any,
    public imagen2ContentType?: string,
    public imagen2?: any,
    public bannerContentType?: string,
    public banner?: any,
    public estadoPublicacion?: EstadoPublicaciond,
    public tipoConsumo?: TipoConsumod,
    public valor?: number,
    public impuesto?: Impuestod,
    public miembro?: IMiembros,
    public categoriaBlog?: ICategoriaContenidos,
    public grupos?: IGrupos
  ) {}
}
