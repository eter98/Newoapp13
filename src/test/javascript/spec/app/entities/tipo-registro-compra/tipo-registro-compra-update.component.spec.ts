/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { NewoApp13TestModule } from '../../../test.module';
import { TipoRegistroCompraUpdateComponent } from 'app/entities/tipo-registro-compra/tipo-registro-compra-update.component';
import { TipoRegistroCompraService } from 'app/entities/tipo-registro-compra/tipo-registro-compra.service';
import { TipoRegistroCompra } from 'app/shared/model/tipo-registro-compra.model';

describe('Component Tests', () => {
  describe('TipoRegistroCompra Management Update Component', () => {
    let comp: TipoRegistroCompraUpdateComponent;
    let fixture: ComponentFixture<TipoRegistroCompraUpdateComponent>;
    let service: TipoRegistroCompraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [TipoRegistroCompraUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoRegistroCompraUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoRegistroCompraUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoRegistroCompraService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoRegistroCompra(123);
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
        const entity = new TipoRegistroCompra();
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
