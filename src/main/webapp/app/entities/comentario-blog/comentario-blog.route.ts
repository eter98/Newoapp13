import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ComentarioBlog } from 'app/shared/model/comentario-blog.model';
import { ComentarioBlogService } from './comentario-blog.service';
import { ComentarioBlogComponent } from './comentario-blog.component';
import { ComentarioBlogDetailComponent } from './comentario-blog-detail.component';
import { ComentarioBlogUpdateComponent } from './comentario-blog-update.component';
import { ComentarioBlogDeletePopupComponent } from './comentario-blog-delete-dialog.component';
import { IComentarioBlog } from 'app/shared/model/comentario-blog.model';

@Injectable({ providedIn: 'root' })
export class ComentarioBlogResolve implements Resolve<IComentarioBlog> {
  constructor(private service: ComentarioBlogService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IComentarioBlog> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ComentarioBlog>) => response.ok),
        map((comentarioBlog: HttpResponse<ComentarioBlog>) => comentarioBlog.body)
      );
    }
    return of(new ComentarioBlog());
  }
}

export const comentarioBlogRoute: Routes = [
  {
    path: '',
    component: ComentarioBlogComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.comentarioBlog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComentarioBlogDetailComponent,
    resolve: {
      comentarioBlog: ComentarioBlogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.comentarioBlog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComentarioBlogUpdateComponent,
    resolve: {
      comentarioBlog: ComentarioBlogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.comentarioBlog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComentarioBlogUpdateComponent,
    resolve: {
      comentarioBlog: ComentarioBlogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.comentarioBlog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const comentarioBlogPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ComentarioBlogDeletePopupComponent,
    resolve: {
      comentarioBlog: ComentarioBlogResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'newoApp13App.comentarioBlog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
