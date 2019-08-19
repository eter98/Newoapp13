/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewoApp13TestModule } from '../../../test.module';
import { TipoRegistroCompraDetailComponent } from 'app/entities/tipo-registro-compra/tipo-registro-compra-detail.component';
import { TipoRegistroCompra } from 'app/shared/model/tipo-registro-compra.model';

describe('Component Tests', () => {
  describe('TipoRegistroCompra Management Detail Component', () => {
    let comp: TipoRegistroCompraDetailComponent;
    let fixture: ComponentFixture<TipoRegistroCompraDetailComponent>;
    const route = ({ data: of({ tipoRegistroCompra: new TipoRegistroCompra(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [TipoRegistroCompraDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoRegistroCompraDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoRegistroCompraDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoRegistroCompra).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
