import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoRegistroCompra } from 'app/shared/model/tipo-registro-compra.model';

type EntityResponseType = HttpResponse<ITipoRegistroCompra>;
type EntityArrayResponseType = HttpResponse<ITipoRegistroCompra[]>;

@Injectable({ providedIn: 'root' })
export class TipoRegistroCompraService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-registro-compras';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/tipo-registro-compras';

  constructor(protected http: HttpClient) {}

  create(tipoRegistroCompra: ITipoRegistroCompra): Observable<EntityResponseType> {
    return this.http.post<ITipoRegistroCompra>(this.resourceUrl, tipoRegistroCompra, { observe: 'response' });
  }

  update(tipoRegistroCompra: ITipoRegistroCompra): Observable<EntityResponseType> {
    return this.http.put<ITipoRegistroCompra>(this.resourceUrl, tipoRegistroCompra, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoRegistroCompra>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoRegistroCompra[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoRegistroCompra[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
