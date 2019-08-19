/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewoApp13TestModule } from '../../../test.module';
import { TipoRegistroCompraDeleteDialogComponent } from 'app/entities/tipo-registro-compra/tipo-registro-compra-delete-dialog.component';
import { TipoRegistroCompraService } from 'app/entities/tipo-registro-compra/tipo-registro-compra.service';

describe('Component Tests', () => {
  describe('TipoRegistroCompra Management Delete Component', () => {
    let comp: TipoRegistroCompraDeleteDialogComponent;
    let fixture: ComponentFixture<TipoRegistroCompraDeleteDialogComponent>;
    let service: TipoRegistroCompraService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [TipoRegistroCompraDeleteDialogComponent]
      })
        .overrideTemplate(TipoRegistroCompraDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoRegistroCompraDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoRegistroCompraService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
