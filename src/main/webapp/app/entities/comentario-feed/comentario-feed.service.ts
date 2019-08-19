import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IComentarioFeed } from 'app/shared/model/comentario-feed.model';

type EntityResponseType = HttpResponse<IComentarioFeed>;
type EntityArrayResponseType = HttpResponse<IComentarioFeed[]>;

@Injectable({ providedIn: 'root' })
export class ComentarioFeedService {
  public resourceUrl = SERVER_API_URL + 'api/comentario-feeds';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/comentario-feeds';

  constructor(protected http: HttpClient) {}

  create(comentarioFeed: IComentarioFeed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comentarioFeed);
    return this.http
      .post<IComentarioFeed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(comentarioFeed: IComentarioFeed): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comentarioFeed);
    return this.http
      .put<IComentarioFeed>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IComentarioFeed>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IComentarioFeed[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IComentarioFeed[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(comentarioFeed: IComentarioFeed): IComentarioFeed {
    const copy: IComentarioFeed = Object.assign({}, comentarioFeed, {
      fecha: comentarioFeed.fecha != null && comentarioFeed.fecha.isValid() ? comentarioFeed.fecha.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((comentarioFeed: IComentarioFeed) => {
        comentarioFeed.fecha = comentarioFeed.fecha != null ? moment(comentarioFeed.fecha) : null;
      });
    }
    return res;
  }
}
