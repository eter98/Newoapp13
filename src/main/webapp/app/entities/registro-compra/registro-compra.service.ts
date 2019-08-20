import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRegistroCompra } from 'app/shared/model/registro-compra.model';

type EntityResponseType = HttpResponse<IRegistroCompra>;
type EntityArrayResponseType = HttpResponse<IRegistroCompra[]>;

@Injectable({ providedIn: 'root' })
export class RegistroCompraService {
  public resourceUrl = SERVER_API_URL + 'api/registro-compras';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/registro-compras';

  constructor(protected http: HttpClient) {}

  create(registroCompra: IRegistroCompra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registroCompra);
    return this.http
      .post<IRegistroCompra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(registroCompra: IRegistroCompra): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(registroCompra);
    return this.http
      .put<IRegistroCompra>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRegistroCompra>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRegistroCompra[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRegistroCompra[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(registroCompra: IRegistroCompra): IRegistroCompra {
    const copy: IRegistroCompra = Object.assign({}, registroCompra, {
      fechaRegistro:
        registroCompra.fechaRegistro != null && registroCompra.fechaRegistro.isValid() ? registroCompra.fechaRegistro.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaRegistro = res.body.fechaRegistro != null ? moment(res.body.fechaRegistro) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((registroCompra: IRegistroCompra) => {
        registroCompra.fechaRegistro = registroCompra.fechaRegistro != null ? moment(registroCompra.fechaRegistro) : null;
      });
    }
    return res;
  }
}
