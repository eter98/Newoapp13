import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewoApp13SharedModule } from 'app/shared';
import {
  RecursosFisicosComponent,
  RecursosFisicosDetailComponent,
  RecursosFisicosUpdateComponent,
  RecursosFisicosDeletePopupComponent,
  RecursosFisicosDeleteDialogComponent,
  recursosFisicosRoute,
  recursosFisicosPopupRoute
} from './';

const ENTITY_STATES = [...recursosFisicosRoute, ...recursosFisicosPopupRoute];

@NgModule({
  imports: [NewoApp13SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RecursosFisicosComponent,
    RecursosFisicosDetailComponent,
    RecursosFisicosUpdateComponent,
    RecursosFisicosDeleteDialogComponent,
    RecursosFisicosDeletePopupComponent
  ],
  entryComponents: [
    RecursosFisicosComponent,
    RecursosFisicosUpdateComponent,
    RecursosFisicosDeleteDialogComponent,
    RecursosFisicosDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13RecursosFisicosModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
