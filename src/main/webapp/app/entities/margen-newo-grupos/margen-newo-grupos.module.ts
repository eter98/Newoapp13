import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewoApp13SharedModule } from 'app/shared';
import {
  MargenNewoGruposComponent,
  MargenNewoGruposDetailComponent,
  MargenNewoGruposUpdateComponent,
  MargenNewoGruposDeletePopupComponent,
  MargenNewoGruposDeleteDialogComponent,
  margenNewoGruposRoute,
  margenNewoGruposPopupRoute
} from './';

const ENTITY_STATES = [...margenNewoGruposRoute, ...margenNewoGruposPopupRoute];

@NgModule({
  imports: [NewoApp13SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MargenNewoGruposComponent,
    MargenNewoGruposDetailComponent,
    MargenNewoGruposUpdateComponent,
    MargenNewoGruposDeleteDialogComponent,
    MargenNewoGruposDeletePopupComponent
  ],
  entryComponents: [
    MargenNewoGruposComponent,
    MargenNewoGruposUpdateComponent,
    MargenNewoGruposDeleteDialogComponent,
    MargenNewoGruposDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13MargenNewoGruposModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
