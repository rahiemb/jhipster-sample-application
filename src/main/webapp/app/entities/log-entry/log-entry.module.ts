import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    LogEntryComponent,
    LogEntryDetailComponent,
    LogEntryUpdateComponent,
    LogEntryDeletePopupComponent,
    LogEntryDeleteDialogComponent,
    logEntryRoute,
    logEntryPopupRoute
} from './';

const ENTITY_STATES = [...logEntryRoute, ...logEntryPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LogEntryComponent,
        LogEntryDetailComponent,
        LogEntryUpdateComponent,
        LogEntryDeleteDialogComponent,
        LogEntryDeletePopupComponent
    ],
    entryComponents: [LogEntryComponent, LogEntryUpdateComponent, LogEntryDeleteDialogComponent, LogEntryDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationLogEntryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
