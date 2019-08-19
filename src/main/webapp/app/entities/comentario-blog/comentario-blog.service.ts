import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IComentarioBlog } from 'app/shared/model/comentario-blog.model';

type EntityResponseType = HttpResponse<IComentarioBlog>;
type EntityArrayResponseType = HttpResponse<IComentarioBlog[]>;

@Injectable({ providedIn: 'root' })
export class ComentarioBlogService {
  public resourceUrl = SERVER_API_URL + 'api/comentario-blogs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/comentario-blogs';

  constructor(protected http: HttpClient) {}

  create(comentarioBlog: IComentarioBlog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comentarioBlog);
    return this.http
      .post<IComentarioBlog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(comentarioBlog: IComentarioBlog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comentarioBlog);
    return this.http
      .put<IComentarioBlog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IComentarioBlog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IComentarioBlog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IComentarioBlog[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(comentarioBlog: IComentarioBlog): IComentarioBlog {
    const copy: IComentarioBlog = Object.assign({}, comentarioBlog, {
      fecha: comentarioBlog.fecha != null && comentarioBlog.fecha.isValid() ? comentarioBlog.fecha.format(DATE_FORMAT) : null
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
      res.body.forEach((comentarioBlog: IComentarioBlog) => {
        comentarioBlog.fecha = comentarioBlog.fecha != null ? moment(comentarioBlog.fecha) : null;
      });
    }
    return res;
  }
}
