/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NewoApp13TestModule } from '../../../test.module';
import { ChatsListadoUpdateComponent } from 'app/entities/chats-listado/chats-listado-update.component';
import { ChatsListadoService } from 'app/entities/chats-listado/chats-listado.service';
import { ChatsListado } from 'app/shared/model/chats-listado.model';

describe('Component Tests', () => {
  describe('ChatsListado Management Update Component', () => {
    let comp: ChatsListadoUpdateComponent;
    let fixture: ComponentFixture<ChatsListadoUpdateComponent>;
    let service: ChatsListadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [ChatsListadoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ChatsListadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChatsListadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChatsListadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ChatsListado(123);
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
        const entity = new ChatsListado();
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
