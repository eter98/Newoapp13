import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComentarioBlog } from 'app/shared/model/comentario-blog.model';
import { ComentarioBlogService } from './comentario-blog.service';

@Component({
  selector: 'jhi-comentario-blog-delete-dialog',
  templateUrl: './comentario-blog-delete-dialog.component.html'
})
export class ComentarioBlogDeleteDialogComponent {
  comentarioBlog: IComentarioBlog;

  constructor(
    protected comentarioBlogService: ComentarioBlogService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.comentarioBlogService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'comentarioBlogListModification',
        content: 'Deleted an comentarioBlog'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-comentario-blog-delete-popup',
  template: ''
})
export class ComentarioBlogDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ comentarioBlog }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ComentarioBlogDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.comentarioBlog = comentarioBlog;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/comentario-blog', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/comentario-blog', { outlets: { popup: null } }]);
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
