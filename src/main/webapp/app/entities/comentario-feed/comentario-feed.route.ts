import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ComentarioFeed } from 'app/shared/model/comentario-feed.model';
import { ComentarioFeedService } from './comentario-feed.service';
import { ComentarioFeedComponent } from './comentario-feed.component';
import { ComentarioFeedDetailComponent } from './comentario-feed-detail.component';
import { ComentarioFeedUpdateComponent } from './comentario-feed-update.component';
import { ComentarioFeedDeletePopupComponent } from './comentario-feed-delete-dialog.component';
import { IComentarioFeed } from 'app/shared/model/comentario-feed.model';

@Injectable({ providedIn: 'root' })
export class ComentarioFeedResolve implements Resolve<IComentarioFeed> {
  constructor(private service: ComentarioFeedService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IComentarioFeed> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ComentarioFeed>) => response.ok),
        map((comentarioFeed: HttpResponse<ComentarioFeed>) => comentarioFeed.body)
      );
    }
    return of(new ComentarioFeed());
  }
}

export const comentarioFeedRoute: Routes = [
  {
    path: '',
    component: ComentarioFeedComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.comentarioFeed.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComentarioFeedDetailComponent,
    resolve: {
      comentarioFeed: ComentarioFeedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.comentarioFeed.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComentarioFeedUpdateComponent,
    resolve: {
      comentarioFeed: ComentarioFeedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.comentarioFeed.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComentarioFeedUpdateComponent,
    resolve: {
      comentarioFeed: ComentarioFeedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.comentarioFeed.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const comentarioFeedPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ComentarioFeedDeletePopupComponent,
    resolve: {
      comentarioFeed: ComentarioFeedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.comentarioFeed.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
