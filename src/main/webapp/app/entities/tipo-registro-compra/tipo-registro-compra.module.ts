import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewoApp13SharedModule } from 'app/shared';
import {
  TipoRegistroCompraComponent,
  TipoRegistroCompraDetailComponent,
  TipoRegistroCompraUpdateComponent,
  TipoRegistroCompraDeletePopupComponent,
  TipoRegistroCompraDeleteDialogComponent,
  tipoRegistroCompraRoute,
  tipoRegistroCompraPopupRoute
} from './';

const ENTITY_STATES = [...tipoRegistroCompraRoute, ...tipoRegistroCompraPopupRoute];

@NgModule({
  imports: [NewoApp13SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoRegistroCompraComponent,
    TipoRegistroCompraDetailComponent,
    TipoRegistroCompraUpdateComponent,
    TipoRegistroCompraDeleteDialogComponent,
    TipoRegistroCompraDeletePopupComponent
  ],
  entryComponents: [
    TipoRegistroCompraComponent,
    TipoRegistroCompraUpdateComponent,
    TipoRegistroCompraDeleteDialogComponent,
    TipoRegistroCompraDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13TipoRegistroCompraModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
