/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewoApp13TestModule } from '../../../test.module';
import { RegistroFacturacionDetailComponent } from 'app/entities/registro-facturacion/registro-facturacion-detail.component';
import { RegistroFacturacion } from 'app/shared/model/registro-facturacion.model';

describe('Component Tests', () => {
  describe('RegistroFacturacion Management Detail Component', () => {
    let comp: RegistroFacturacionDetailComponent;
    let fixture: ComponentFixture<RegistroFacturacionDetailComponent>;
    const route = ({ data: of({ registroFacturacion: new RegistroFacturacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [RegistroFacturacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RegistroFacturacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RegistroFacturacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.registroFacturacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
