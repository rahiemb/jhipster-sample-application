import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    TitleComponent,
    TitleDetailComponent,
    TitleUpdateComponent,
    TitleDeletePopupComponent,
    TitleDeleteDialogComponent,
    titleRoute,
    titlePopupRoute
} from './';

const ENTITY_STATES = [...titleRoute, ...titlePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TitleComponent, TitleDetailComponent, TitleUpdateComponent, TitleDeleteDialogComponent, TitleDeletePopupComponent],
    entryComponents: [TitleComponent, TitleUpdateComponent, TitleDeleteDialogComponent, TitleDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationTitleModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
