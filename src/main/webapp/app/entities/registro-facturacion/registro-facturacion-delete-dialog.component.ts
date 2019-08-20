import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRegistroFacturacion } from 'app/shared/model/registro-facturacion.model';
import { RegistroFacturacionService } from './registro-facturacion.service';

@Component({
  selector: 'jhi-registro-facturacion-delete-dialog',
  templateUrl: './registro-facturacion-delete-dialog.component.html'
})
export class RegistroFacturacionDeleteDialogComponent {
  registroFacturacion: IRegistroFacturacion;

  constructor(
    protected registroFacturacionService: RegistroFacturacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.registroFacturacionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'registroFacturacionListModification',
        content: 'Deleted an registroFacturacion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-registro-facturacion-delete-popup',
  template: ''
})
export class RegistroFacturacionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ registroFacturacion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RegistroFacturacionDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.registroFacturacion = registroFacturacion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/registro-facturacion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/registro-facturacion', { outlets: { popup: null } }]);
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
