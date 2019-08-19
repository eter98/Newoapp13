/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NewoApp13TestModule } from '../../../test.module';
import { EntradaMiembrosUpdateComponent } from 'app/entities/entrada-miembros/entrada-miembros-update.component';
import { EntradaMiembrosService } from 'app/entities/entrada-miembros/entrada-miembros.service';
import { EntradaMiembros } from 'app/shared/model/entrada-miembros.model';

describe('Component Tests', () => {
  describe('EntradaMiembros Management Update Component', () => {
    let comp: EntradaMiembrosUpdateComponent;
    let fixture: ComponentFixture<EntradaMiembrosUpdateComponent>;
    let service: EntradaMiembrosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [EntradaMiembrosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EntradaMiembrosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntradaMiembrosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntradaMiembrosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EntradaMiembros(123);
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
        const entity = new EntradaMiembros();
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
