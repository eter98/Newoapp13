import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewoApp13SharedModule } from 'app/shared';
import {
  MargenNewoBlogComponent,
  MargenNewoBlogDetailComponent,
  MargenNewoBlogUpdateComponent,
  MargenNewoBlogDeletePopupComponent,
  MargenNewoBlogDeleteDialogComponent,
  margenNewoBlogRoute,
  margenNewoBlogPopupRoute
} from './';

const ENTITY_STATES = [...margenNewoBlogRoute, ...margenNewoBlogPopupRoute];

@NgModule({
  imports: [NewoApp13SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MargenNewoBlogComponent,
    MargenNewoBlogDetailComponent,
    MargenNewoBlogUpdateComponent,
    MargenNewoBlogDeleteDialogComponent,
    MargenNewoBlogDeletePopupComponent
  ],
  entryComponents: [
    MargenNewoBlogComponent,
    MargenNewoBlogUpdateComponent,
    MargenNewoBlogDeleteDialogComponent,
    MargenNewoBlogDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13MargenNewoBlogModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
