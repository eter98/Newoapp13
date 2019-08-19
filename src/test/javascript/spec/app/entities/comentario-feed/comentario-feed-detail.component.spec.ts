/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewoApp13TestModule } from '../../../test.module';
import { ComentarioFeedDetailComponent } from 'app/entities/comentario-feed/comentario-feed-detail.component';
import { ComentarioFeed } from 'app/shared/model/comentario-feed.model';

describe('Component Tests', () => {
  describe('ComentarioFeed Management Detail Component', () => {
    let comp: ComentarioFeedDetailComponent;
    let fixture: ComponentFixture<ComentarioFeedDetailComponent>;
    const route = ({ data: of({ comentarioFeed: new ComentarioFeed(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [ComentarioFeedDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ComentarioFeedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ComentarioFeedDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.comentarioFeed).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
