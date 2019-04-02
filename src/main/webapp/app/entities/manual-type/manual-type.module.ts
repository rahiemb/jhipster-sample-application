import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    ManualTypeComponent,
    ManualTypeDetailComponent,
    ManualTypeUpdateComponent,
    ManualTypeDeletePopupComponent,
    ManualTypeDeleteDialogComponent,
    manualTypeRoute,
    manualTypePopupRoute
} from './';

const ENTITY_STATES = [...manualTypeRoute, ...manualTypePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ManualTypeComponent,
        ManualTypeDetailComponent,
        ManualTypeUpdateComponent,
        ManualTypeDeleteDialogComponent,
        ManualTypeDeletePopupComponent
    ],
    entryComponents: [ManualTypeComponent, ManualTypeUpdateComponent, ManualTypeDeleteDialogComponent, ManualTypeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationManualTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
