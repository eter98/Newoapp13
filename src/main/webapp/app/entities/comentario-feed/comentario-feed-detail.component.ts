import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IComentarioFeed } from 'app/shared/model/comentario-feed.model';

@Component({
  selector: 'jhi-comentario-feed-detail',
  templateUrl: './comentario-feed-detail.component.html'
})
export class ComentarioFeedDetailComponent implements OnInit {
  comentarioFeed: IComentarioFeed;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ comentarioFeed }) => {
      this.comentarioFeed = comentarioFeed;
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
