/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewoApp13TestModule } from '../../../test.module';
import { ChatsListadoDetailComponent } from 'app/entities/chats-listado/chats-listado-detail.component';
import { ChatsListado } from 'app/shared/model/chats-listado.model';

describe('Component Tests', () => {
  describe('ChatsListado Management Detail Component', () => {
    let comp: ChatsListadoDetailComponent;
    let fixture: ComponentFixture<ChatsListadoDetailComponent>;
    const route = ({ data: of({ chatsListado: new ChatsListado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [ChatsListadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ChatsListadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChatsListadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.chatsListado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
