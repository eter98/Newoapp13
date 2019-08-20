/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NewoApp13TestModule } from '../../../test.module';
import { RegistroFacturacionUpdateComponent } from 'app/entities/registro-facturacion/registro-facturacion-update.component';
import { RegistroFacturacionService } from 'app/entities/registro-facturacion/registro-facturacion.service';
import { RegistroFacturacion } from 'app/shared/model/registro-facturacion.model';

describe('Component Tests', () => {
  describe('RegistroFacturacion Management Update Component', () => {
    let comp: RegistroFacturacionUpdateComponent;
    let fixture: ComponentFixture<RegistroFacturacionUpdateComponent>;
    let service: RegistroFacturacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [RegistroFacturacionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RegistroFacturacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RegistroFacturacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RegistroFacturacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RegistroFacturacion(123);
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
        const entity = new RegistroFacturacion();
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
