import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoRegistroCompra } from 'app/shared/model/tipo-registro-compra.model';
import { TipoRegistroCompraService } from './tipo-registro-compra.service';

@Component({
  selector: 'jhi-tipo-registro-compra-delete-dialog',
  templateUrl: './tipo-registro-compra-delete-dialog.component.html'
})
export class TipoRegistroCompraDeleteDialogComponent {
  tipoRegistroCompra: ITipoRegistroCompra;

  constructor(
    protected tipoRegistroCompraService: TipoRegistroCompraService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipoRegistroCompraService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipoRegistroCompraListModification',
        content: 'Deleted an tipoRegistroCompra'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipo-registro-compra-delete-popup',
  template: ''
})
export class TipoRegistroCompraDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoRegistroCompra }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipoRegistroCompraDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipoRegistroCompra = tipoRegistroCompra;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tipo-registro-compra', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tipo-registro-compra', { outlets: { popup: null } }]);
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
