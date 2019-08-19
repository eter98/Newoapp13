import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRegistroFacturacion } from 'app/shared/model/registro-facturacion.model';

type EntityResponseType = HttpResponse<IRegistroFacturacion>;
type EntityArrayResponseType = HttpResponse<IRegistroFacturacion[]>;

@Injectable({ providedIn: 'root' })
export class RegistroFacturacionService {
  public resourceUrl = SERVER_API_URL + 'api/registro-facturacions';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/registro-facturacions';

  constructor(protected http: HttpClient) {}

  create(registroFacturacion: IRegistroFacturacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registroFacturacion);
    return this.http
      .post<IRegistroFacturacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(registroFacturacion: IRegistroFacturacion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registroFacturacion);
    return this.http
      .put<IRegistroFacturacion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRegistroFacturacion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRegistroFacturacion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRegistroFacturacion[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(registroFacturacion: IRegistroFacturacion): IRegistroFacturacion {
    const copy: IRegistroFacturacion = Object.assign({}, registroFacturacion, {
      fechaRegistro:
        registroFacturacion.fechaRegistro != null && registroFacturacion.fechaRegistro.isValid()
          ? registroFacturacion.fechaRegistro.toJSON()
          : null,
      fechaFacturacion:
        registroFacturacion.fechaFacturacion != null && registroFacturacion.fechaFacturacion.isValid()
          ? registroFacturacion.fechaFacturacion.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaRegistro = res.body.fechaRegistro != null ? moment(res.body.fechaRegistro) : null;
      res.body.fechaFacturacion = res.body.fechaFacturacion != null ? moment(res.body.fechaFacturacion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((registroFacturacion: IRegistroFacturacion) => {
        registroFacturacion.fechaRegistro = registroFacturacion.fechaRegistro != null ? moment(registroFacturacion.fechaRegistro) : null;
        registroFacturacion.fechaFacturacion =
          registroFacturacion.fechaFacturacion != null ? moment(registroFacturacion.fechaFacturacion) : null;
      });
    }
    return res;
  }
}
