import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewoApp13SharedModule } from 'app/shared';
import {
  RegistroFacturacionComponent,
  RegistroFacturacionDetailComponent,
  RegistroFacturacionUpdateComponent,
  RegistroFacturacionDeletePopupComponent,
  RegistroFacturacionDeleteDialogComponent,
  registroFacturacionRoute,
  registroFacturacionPopupRoute
} from './';

const ENTITY_STATES = [...registroFacturacionRoute, ...registroFacturacionPopupRoute];

@NgModule({
  imports: [NewoApp13SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RegistroFacturacionComponent,
    RegistroFacturacionDetailComponent,
    RegistroFacturacionUpdateComponent,
    RegistroFacturacionDeleteDialogComponent,
    RegistroFacturacionDeletePopupComponent
  ],
  entryComponents: [
    RegistroFacturacionComponent,
    RegistroFacturacionUpdateComponent,
    RegistroFacturacionDeleteDialogComponent,
    RegistroFacturacionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13RegistroFacturacionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
