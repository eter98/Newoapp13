import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoEspacio } from 'app/shared/model/tipo-espacio.model';

type EntityResponseType = HttpResponse<ITipoEspacio>;
type EntityArrayResponseType = HttpResponse<ITipoEspacio[]>;

@Injectable({ providedIn: 'root' })
export class TipoEspacioService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-espacios';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/tipo-espacios';

  constructor(protected http: HttpClient) {}

  create(tipoEspacio: ITipoEspacio): Observable<EntityResponseType> {
    return this.http.post<ITipoEspacio>(this.resourceUrl, tipoEspacio, { observe: 'response' });
  }

  update(tipoEspacio: ITipoEspacio): Observable<EntityResponseType> {
    return this.http.put<ITipoEspacio>(this.resourceUrl, tipoEspacio, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoEspacio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoEspacio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoEspacio[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
