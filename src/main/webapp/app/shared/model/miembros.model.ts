import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IPais } from 'app/shared/model/pais.model';
import { ISedes } from 'app/shared/model/sedes.model';

export const enum TipoDocumentod {
  Cedula = 'Cedula',
  Cedula_Extranjeria = 'Cedula_Extranjeria',
  Pasaporte = 'Pasaporte',
  Otro = 'Otro'
}

export const enum Generod {
  Masculino = 'Masculino',
  Femenino = 'Femenino',
  Otro = 'Otro'
}

export interface IMiembros {
  id?: number;
  tipoDocumento?: TipoDocumentod;
  identificacion?: number;
  fechaNacimiento?: Moment;
  fechaRegistro?: Moment;
  genero?: Generod;
  celular?: string;
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
  derechosDeCompra?: boolean;
  accesoIlimitado?: boolean;
  aliado?: boolean;
  host?: boolean;
  miembro?: IUser;
  nacionalidad?: IPais;
  sede?: ISedes;
}

export class Miembros implements IMiembros {
  constructor(
    public id?: number,
    public tipoDocumento?: TipoDocumentod,
    public identificacion?: number,
    public fechaNacimiento?: Moment,
    public fechaRegistro?: Moment,
    public genero?: Generod,
    public celular?: string,
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
    public derechosDeCompra?: boolean,
    public accesoIlimitado?: boolean,
    public aliado?: boolean,
    public host?: boolean,
    public miembro?: IUser,
    public nacionalidad?: IPais,
    public sede?: ISedes
  ) {
    this.derechosDeCompra = this.derechosDeCompra || false;
    this.accesoIlimitado = this.accesoIlimitado || false;
    this.aliado = this.aliado || false;
    this.host = this.host || false;
  }
}
