import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewoApp13SharedModule } from 'app/shared';
import {
  CuentaAsociadaComponent,
  CuentaAsociadaDetailComponent,
  CuentaAsociadaUpdateComponent,
  CuentaAsociadaDeletePopupComponent,
  CuentaAsociadaDeleteDialogComponent,
  cuentaAsociadaRoute,
  cuentaAsociadaPopupRoute
} from './';

const ENTITY_STATES = [...cuentaAsociadaRoute, ...cuentaAsociadaPopupRoute];

@NgModule({
  imports: [NewoApp13SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CuentaAsociadaComponent,
    CuentaAsociadaDetailComponent,
    CuentaAsociadaUpdateComponent,
    CuentaAsociadaDeleteDialogComponent,
    CuentaAsociadaDeletePopupComponent
  ],
  entryComponents: [
    CuentaAsociadaComponent,
    CuentaAsociadaUpdateComponent,
    CuentaAsociadaDeleteDialogComponent,
    CuentaAsociadaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13CuentaAsociadaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
