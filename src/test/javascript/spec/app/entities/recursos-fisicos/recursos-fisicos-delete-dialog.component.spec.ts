/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewoApp13TestModule } from '../../../test.module';
import { RecursosFisicosDeleteDialogComponent } from 'app/entities/recursos-fisicos/recursos-fisicos-delete-dialog.component';
import { RecursosFisicosService } from 'app/entities/recursos-fisicos/recursos-fisicos.service';

describe('Component Tests', () => {
  describe('RecursosFisicos Management Delete Component', () => {
    let comp: RecursosFisicosDeleteDialogComponent;
    let fixture: ComponentFixture<RecursosFisicosDeleteDialogComponent>;
    let service: RecursosFisicosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [RecursosFisicosDeleteDialogComponent]
      })
        .overrideTemplate(RecursosFisicosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RecursosFisicosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RecursosFisicosService);
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
