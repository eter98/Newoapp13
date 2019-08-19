import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ChatsListado } from 'app/shared/model/chats-listado.model';
import { ChatsListadoService } from './chats-listado.service';
import { ChatsListadoComponent } from './chats-listado.component';
import { ChatsListadoDetailComponent } from './chats-listado-detail.component';
import { ChatsListadoUpdateComponent } from './chats-listado-update.component';
import { ChatsListadoDeletePopupComponent } from './chats-listado-delete-dialog.component';
import { IChatsListado } from 'app/shared/model/chats-listado.model';

@Injectable({ providedIn: 'root' })
export class ChatsListadoResolve implements Resolve<IChatsListado> {
  constructor(private service: ChatsListadoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IChatsListado> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ChatsListado>) => response.ok),
        map((chatsListado: HttpResponse<ChatsListado>) => chatsListado.body)
      );
    }
    return of(new ChatsListado());
  }
}

export const chatsListadoRoute: Routes = [
  {
    path: '',
    component: ChatsListadoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.chatsListado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ChatsListadoDetailComponent,
    resolve: {
      chatsListado: ChatsListadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.chatsListado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ChatsListadoUpdateComponent,
    resolve: {
      chatsListado: ChatsListadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.chatsListado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ChatsListadoUpdateComponent,
    resolve: {
      chatsListado: ChatsListadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.chatsListado.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const chatsListadoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ChatsListadoDeletePopupComponent,
    resolve: {
      chatsListado: ChatsListadoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.chatsListado.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
