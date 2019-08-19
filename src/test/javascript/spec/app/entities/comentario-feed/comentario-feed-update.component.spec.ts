/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NewoApp13TestModule } from '../../../test.module';
import { ComentarioFeedUpdateComponent } from 'app/entities/comentario-feed/comentario-feed-update.component';
import { ComentarioFeedService } from 'app/entities/comentario-feed/comentario-feed.service';
import { ComentarioFeed } from 'app/shared/model/comentario-feed.model';

describe('Component Tests', () => {
  describe('ComentarioFeed Management Update Component', () => {
    let comp: ComentarioFeedUpdateComponent;
    let fixture: ComponentFixture<ComentarioFeedUpdateComponent>;
    let service: ComentarioFeedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [ComentarioFeedUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ComentarioFeedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComentarioFeedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ComentarioFeedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ComentarioFeed(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ComentarioFeed();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
