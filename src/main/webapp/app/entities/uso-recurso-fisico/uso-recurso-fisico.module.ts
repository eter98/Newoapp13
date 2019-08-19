import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewoApp13SharedModule } from 'app/shared';
import {
  UsoRecursoFisicoComponent,
  UsoRecursoFisicoDetailComponent,
  UsoRecursoFisicoUpdateComponent,
  UsoRecursoFisicoDeletePopupComponent,
  UsoRecursoFisicoDeleteDialogComponent,
  usoRecursoFisicoRoute,
  usoRecursoFisicoPopupRoute
} from './';

const ENTITY_STATES = [...usoRecursoFisicoRoute, ...usoRecursoFisicoPopupRoute];

@NgModule({
  imports: [NewoApp13SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UsoRecursoFisicoComponent,
    UsoRecursoFisicoDetailComponent,
    UsoRecursoFisicoUpdateComponent,
    UsoRecursoFisicoDeleteDialogComponent,
    UsoRecursoFisicoDeletePopupComponent
  ],
  entryComponents: [
    UsoRecursoFisicoComponent,
    UsoRecursoFisicoUpdateComponent,
    UsoRecursoFisicoDeleteDialogComponent,
    UsoRecursoFisicoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13UsoRecursoFisicoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
