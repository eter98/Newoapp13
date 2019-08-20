import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewoApp13SharedModule } from 'app/shared';
import {
  FacturacionComponent,
  FacturacionDetailComponent,
  FacturacionUpdateComponent,
  FacturacionDeletePopupComponent,
  FacturacionDeleteDialogComponent,
  facturacionRoute,
  facturacionPopupRoute
} from './';

const ENTITY_STATES = [...facturacionRoute, ...facturacionPopupRoute];

@NgModule({
  imports: [NewoApp13SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FacturacionComponent,
    FacturacionDetailComponent,
    FacturacionUpdateComponent,
    FacturacionDeleteDialogComponent,
    FacturacionDeletePopupComponent
  ],
  entryComponents: [FacturacionComponent, FacturacionUpdateComponent, FacturacionDeleteDialogComponent, FacturacionDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13FacturacionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
