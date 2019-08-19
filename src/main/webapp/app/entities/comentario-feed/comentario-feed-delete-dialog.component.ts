import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComentarioFeed } from 'app/shared/model/comentario-feed.model';
import { ComentarioFeedService } from './comentario-feed.service';

@Component({
  selector: 'jhi-comentario-feed-delete-dialog',
  templateUrl: './comentario-feed-delete-dialog.component.html'
})
export class ComentarioFeedDeleteDialogComponent {
  comentarioFeed: IComentarioFeed;

  constructor(
    protected comentarioFeedService: ComentarioFeedService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.comentarioFeedService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'comentarioFeedListModification',
        content: 'Deleted an comentarioFeed'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-comentario-feed-delete-popup',
  template: ''
})
export class ComentarioFeedDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ comentarioFeed }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ComentarioFeedDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.comentarioFeed = comentarioFeed;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/comentario-feed', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/comentario-feed', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
