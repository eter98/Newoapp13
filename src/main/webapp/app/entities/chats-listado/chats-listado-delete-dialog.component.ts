import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChatsListado } from 'app/shared/model/chats-listado.model';
import { ChatsListadoService } from './chats-listado.service';

@Component({
  selector: 'jhi-chats-listado-delete-dialog',
  templateUrl: './chats-listado-delete-dialog.component.html'
})
export class ChatsListadoDeleteDialogComponent {
  chatsListado: IChatsListado;

  constructor(
    protected chatsListadoService: ChatsListadoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.chatsListadoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'chatsListadoListModification',
        content: 'Deleted an chatsListado'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-chats-listado-delete-popup',
  template: ''
})
export class ChatsListadoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ chatsListado }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ChatsListadoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.chatsListado = chatsListado;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/chats-listado', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/chats-listado', { outlets: { popup: null } }]);
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
