import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Chat } from 'app/shared/model/chat.model';
import { ChatService } from './chat.service';
import { ChatComponent } from './chat.component';
import { ChatDetailComponent } from './chat-detail.component';
import { ChatUpdateComponent } from './chat-update.component';
import { ChatDeletePopupComponent } from './chat-delete-dialog.component';
import { IChat } from 'app/shared/model/chat.model';

@Injectable({ providedIn: 'root' })
export class ChatResolve implements Resolve<IChat> {
  constructor(private service: ChatService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IChat> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Chat>) => response.ok),
        map((chat: HttpResponse<Chat>) => chat.body)
      );
    }
    return of(new Chat());
  }
}

export const chatRoute: Routes = [
  {
    path: '',
    component: ChatComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.chat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ChatDetailComponent,
    resolve: {
      chat: ChatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.chat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ChatUpdateComponent,
    resolve: {
      chat: ChatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.chat.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ChatUpdateComponent,
    resolve: {
      chat: ChatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.chat.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const chatPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ChatDeletePopupComponent,
    resolve: {
      chat: ChatResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.chat.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
