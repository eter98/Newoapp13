/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewoApp13TestModule } from '../../../test.module';
import { RegistroFacturacionDeleteDialogComponent } from 'app/entities/registro-facturacion/registro-facturacion-delete-dialog.component';
import { RegistroFacturacionService } from 'app/entities/registro-facturacion/registro-facturacion.service';

describe('Component Tests', () => {
  describe('RegistroFacturacion Management Delete Component', () => {
    let comp: RegistroFacturacionDeleteDialogComponent;
    let fixture: ComponentFixture<RegistroFacturacionDeleteDialogComponent>;
    let service: RegistroFacturacionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [RegistroFacturacionDeleteDialogComponent]
      })
        .overrideTemplate(RegistroFacturacionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegistroFacturacionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegistroFacturacionService);
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
