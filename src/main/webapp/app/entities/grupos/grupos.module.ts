import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewoApp13SharedModule } from 'app/shared';
import {
  GruposComponent,
  GruposDetailComponent,
  GruposUpdateComponent,
  GruposDeletePopupComponent,
  GruposDeleteDialogComponent,
  gruposRoute,
  gruposPopupRoute
} from './';

const ENTITY_STATES = [...gruposRoute, ...gruposPopupRoute];

@NgModule({
  imports: [NewoApp13SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [GruposComponent, GruposDetailComponent, GruposUpdateComponent, GruposDeleteDialogComponent, GruposDeletePopupComponent],
  entryComponents: [GruposComponent, GruposUpdateComponent, GruposDeleteDialogComponent, GruposDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13GruposModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
