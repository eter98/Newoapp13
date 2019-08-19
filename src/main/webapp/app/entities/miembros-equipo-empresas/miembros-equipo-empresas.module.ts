import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { NewoApp13SharedModule } from 'app/shared';
import {
  MiembrosEquipoEmpresasComponent,
  MiembrosEquipoEmpresasDetailComponent,
  MiembrosEquipoEmpresasUpdateComponent,
  MiembrosEquipoEmpresasDeletePopupComponent,
  MiembrosEquipoEmpresasDeleteDialogComponent,
  miembrosEquipoEmpresasRoute,
  miembrosEquipoEmpresasPopupRoute
} from './';

const ENTITY_STATES = [...miembrosEquipoEmpresasRoute, ...miembrosEquipoEmpresasPopupRoute];

@NgModule({
  imports: [NewoApp13SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MiembrosEquipoEmpresasComponent,
    MiembrosEquipoEmpresasDetailComponent,
    MiembrosEquipoEmpresasUpdateComponent,
    MiembrosEquipoEmpresasDeleteDialogComponent,
    MiembrosEquipoEmpresasDeletePopupComponent
  ],
  entryComponents: [
    MiembrosEquipoEmpresasComponent,
    MiembrosEquipoEmpresasUpdateComponent,
    MiembrosEquipoEmpresasDeleteDialogComponent,
    MiembrosEquipoEmpresasDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13MiembrosEquipoEmpresasModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
