/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewoApp13TestModule } from '../../../test.module';
import { ChatsListadoDeleteDialogComponent } from 'app/entities/chats-listado/chats-listado-delete-dialog.component';
import { ChatsListadoService } from 'app/entities/chats-listado/chats-listado.service';

describe('Component Tests', () => {
  describe('ChatsListado Management Delete Component', () => {
    let comp: ChatsListadoDeleteDialogComponent;
    let fixture: ComponentFixture<ChatsListadoDeleteDialogComponent>;
    let service: ChatsListadoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [ChatsListadoDeleteDialogComponent]
      })
        .overrideTemplate(ChatsListadoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChatsListadoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChatsListadoService);
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
