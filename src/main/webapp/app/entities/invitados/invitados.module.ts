import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewoApp13SharedModule } from 'app/shared';
import {
  InvitadosComponent,
  InvitadosDetailComponent,
  InvitadosUpdateComponent,
  InvitadosDeletePopupComponent,
  InvitadosDeleteDialogComponent,
  invitadosRoute,
  invitadosPopupRoute
} from './';

const ENTITY_STATES = [...invitadosRoute, ...invitadosPopupRoute];

@NgModule({
  imports: [NewoApp13SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InvitadosComponent,
    InvitadosDetailComponent,
    InvitadosUpdateComponent,
    InvitadosDeleteDialogComponent,
    InvitadosDeletePopupComponent
  ],
  entryComponents: [InvitadosComponent, InvitadosUpdateComponent, InvitadosDeleteDialogComponent, InvitadosDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13InvitadosModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
