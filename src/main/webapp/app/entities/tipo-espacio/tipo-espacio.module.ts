import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewoApp13SharedModule } from 'app/shared';
import {
  TipoEspacioComponent,
  TipoEspacioDetailComponent,
  TipoEspacioUpdateComponent,
  TipoEspacioDeletePopupComponent,
  TipoEspacioDeleteDialogComponent,
  tipoEspacioRoute,
  tipoEspacioPopupRoute
} from './';

const ENTITY_STATES = [...tipoEspacioRoute, ...tipoEspacioPopupRoute];

@NgModule({
  imports: [NewoApp13SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoEspacioComponent,
    TipoEspacioDetailComponent,
    TipoEspacioUpdateComponent,
    TipoEspacioDeleteDialogComponent,
    TipoEspacioDeletePopupComponent
  ],
  entryComponents: [TipoEspacioComponent, TipoEspacioUpdateComponent, TipoEspacioDeleteDialogComponent, TipoEspacioDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13TipoEspacioModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
