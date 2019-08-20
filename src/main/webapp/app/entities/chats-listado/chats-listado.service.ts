import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChatsListado } from 'app/shared/model/chats-listado.model';

type EntityResponseType = HttpResponse<IChatsListado>;
type EntityArrayResponseType = HttpResponse<IChatsListado[]>;

@Injectable({ providedIn: 'root' })
export class ChatsListadoService {
  public resourceUrl = SERVER_API_URL + 'api/chats-listados';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/chats-listados';

  constructor(protected http: HttpClient) {}

  create(chatsListado: IChatsListado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chatsListado);
    return this.http
      .post<IChatsListado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(chatsListado: IChatsListado): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chatsListado);
    return this.http
      .put<IChatsListado>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IChatsListado>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChatsListado[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChatsListado[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(chatsListado: IChatsListado): IChatsListado {
    const copy: IChatsListado = Object.assign({}, chatsListado, {
      sendTime: chatsListado.sendTime != null && chatsListado.sendTime.isValid() ? chatsListado.sendTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.sendTime = res.body.sendTime != null ? moment(res.body.sendTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((chatsListado: IChatsListado) => {
        chatsListado.sendTime = chatsListado.sendTime != null ? moment(chatsListado.sendTime) : null;
      });
    }
    return res;
  }
}
