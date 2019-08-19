import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RegistroFacturacion } from 'app/shared/model/registro-facturacion.model';
import { RegistroFacturacionService } from './registro-facturacion.service';
import { RegistroFacturacionComponent } from './registro-facturacion.component';
import { RegistroFacturacionDetailComponent } from './registro-facturacion-detail.component';
import { RegistroFacturacionUpdateComponent } from './registro-facturacion-update.component';
import { RegistroFacturacionDeletePopupComponent } from './registro-facturacion-delete-dialog.component';
import { IRegistroFacturacion } from 'app/shared/model/registro-facturacion.model';

@Injectable({ providedIn: 'root' })
export class RegistroFacturacionResolve implements Resolve<IRegistroFacturacion> {
  constructor(private service: RegistroFacturacionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRegistroFacturacion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RegistroFacturacion>) => response.ok),
        map((registroFacturacion: HttpResponse<RegistroFacturacion>) => registroFacturacion.body)
      );
    }
    return of(new RegistroFacturacion());
  }
}

export const registroFacturacionRoute: Routes = [
  {
    path: '',
    component: RegistroFacturacionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.registroFacturacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RegistroFacturacionDetailComponent,
    resolve: {
      registroFacturacion: RegistroFacturacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.registroFacturacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RegistroFacturacionUpdateComponent,
    resolve: {
      registroFacturacion: RegistroFacturacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.registroFacturacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RegistroFacturacionUpdateComponent,
    resolve: {
      registroFacturacion: RegistroFacturacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.registroFacturacion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const registroFacturacionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RegistroFacturacionDeletePopupComponent,
    resolve: {
      registroFacturacion: RegistroFacturacionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.registroFacturacion.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
