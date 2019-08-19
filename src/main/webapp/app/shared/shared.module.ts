import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NewoApp13SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [NewoApp13SharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [NewoApp13SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewoApp13SharedModule {
  static forRoot() {
    return {
      ngModule: NewoApp13SharedModule
    };
  }
}
