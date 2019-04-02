import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    VersionSummaryComponent,
    VersionSummaryDetailComponent,
    VersionSummaryUpdateComponent,
    VersionSummaryDeletePopupComponent,
    VersionSummaryDeleteDialogComponent,
    versionSummaryRoute,
    versionSummaryPopupRoute
} from './';

const ENTITY_STATES = [...versionSummaryRoute, ...versionSummaryPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VersionSummaryComponent,
        VersionSummaryDetailComponent,
        VersionSummaryUpdateComponent,
        VersionSummaryDeleteDialogComponent,
        VersionSummaryDeletePopupComponent
    ],
    entryComponents: [
        VersionSummaryComponent,
        VersionSummaryUpdateComponent,
        VersionSummaryDeleteDialogComponent,
        VersionSummaryDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationVersionSummaryModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
