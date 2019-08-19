import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IComentarioBlog } from 'app/shared/model/comentario-blog.model';

@Component({
  selector: 'jhi-comentario-blog-detail',
  templateUrl: './comentario-blog-detail.component.html'
})
export class ComentarioBlogDetailComponent implements OnInit {
  comentarioBlog: IComentarioBlog;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ comentarioBlog }) => {
      this.comentarioBlog = comentarioBlog;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
