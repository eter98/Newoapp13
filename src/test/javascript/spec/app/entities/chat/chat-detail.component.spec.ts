/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NewoApp13TestModule } from '../../../test.module';
import { ChatDetailComponent } from 'app/entities/chat/chat-detail.component';
import { Chat } from 'app/shared/model/chat.model';

describe('Component Tests', () => {
  describe('Chat Management Detail Component', () => {
    let comp: ChatDetailComponent;
    let fixture: ComponentFixture<ChatDetailComponent>;
    const route = ({ data: of({ chat: new Chat(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NewoApp13TestModule],
        declarations: [ChatDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ChatDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChatDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.chat).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
