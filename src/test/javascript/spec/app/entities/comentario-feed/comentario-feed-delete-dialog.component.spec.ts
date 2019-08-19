/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NewoApp13TestModule } from '../../../test.module';
import { ComentarioFeedDeleteDialogComponent } from 'app/entities/comentario-feed/comentario-feed-delete-dialog.component';
import { ComentarioFeedService } from 'app/entities/comentario-feed/comentario-feed.service';

describe('Component Tests', () => {
  describe('ComentarioFeed Management Delete Component', () => {
    let comp: ComentarioFeedDeleteDialogComponent;
    let fixture: ComponentFixture<ComentarioFeedDeleteDialogComponent>;
    let service: ComentarioFeedService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [ComentarioFeedDeleteDialogComponent]
      })
        .overrideTemplate(ComentarioFeedDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComentarioFeedDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComentarioFeedService);
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
