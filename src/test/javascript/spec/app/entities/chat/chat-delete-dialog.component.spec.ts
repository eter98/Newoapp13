/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewoApp13TestModule } from '../../../test.module';
import { ChatDeleteDialogComponent } from 'app/entities/chat/chat-delete-dialog.component';
import { ChatService } from 'app/entities/chat/chat.service';

describe('Component Tests', () => {
  describe('Chat Management Delete Component', () => {
    let comp: ChatDeleteDialogComponent;
    let fixture: ComponentFixture<ChatDeleteDialogComponent>;
    let service: ChatService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [ChatDeleteDialogComponent]
      })
        .overrideTemplate(ChatDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChatDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChatService);
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
