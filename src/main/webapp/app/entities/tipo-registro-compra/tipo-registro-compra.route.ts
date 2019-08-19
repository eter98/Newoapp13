import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoRegistroCompra } from 'app/shared/model/tipo-registro-compra.model';
import { TipoRegistroCompraService } from './tipo-registro-compra.service';
import { TipoRegistroCompraComponent } from './tipo-registro-compra.component';
import { TipoRegistroCompraDetailComponent } from './tipo-registro-compra-detail.component';
import { TipoRegistroCompraUpdateComponent } from './tipo-registro-compra-update.component';
import { TipoRegistroCompraDeletePopupComponent } from './tipo-registro-compra-delete-dialog.component';
import { ITipoRegistroCompra } from 'app/shared/model/tipo-registro-compra.model';

@Injectable({ providedIn: 'root' })
export class TipoRegistroCompraResolve implements Resolve<ITipoRegistroCompra> {
  constructor(private service: TipoRegistroCompraService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoRegistroCompra> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TipoRegistroCompra>) => response.ok),
        map((tipoRegistroCompra: HttpResponse<TipoRegistroCompra>) => tipoRegistroCompra.body)
      );
    }
    return of(new TipoRegistroCompra());
  }
}

export const tipoRegistroCompraRoute: Routes = [
  {
    path: '',
    component: TipoRegistroCompraComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.tipoRegistroCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoRegistroCompraDetailComponent,
    resolve: {
      tipoRegistroCompra: TipoRegistroCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.tipoRegistroCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoRegistroCompraUpdateComponent,
    resolve: {
      tipoRegistroCompra: TipoRegistroCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.tipoRegistroCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoRegistroCompraUpdateComponent,
    resolve: {
      tipoRegistroCompra: TipoRegistroCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.tipoRegistroCompra.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipoRegistroCompraPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipoRegistroCompraDeletePopupComponent,
    resolve: {
      tipoRegistroCompra: TipoRegistroCompraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.tipoRegistroCompra.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
