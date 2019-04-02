import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    SectionComponent,
    SectionDetailComponent,
    SectionUpdateComponent,
    SectionDeletePopupComponent,
    SectionDeleteDialogComponent,
    sectionRoute,
    sectionPopupRoute
} from './';

const ENTITY_STATES = [...sectionRoute, ...sectionPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SectionComponent,
        SectionDetailComponent,
        SectionUpdateComponent,
        SectionDeleteDialogComponent,
        SectionDeletePopupComponent
    ],
    entryComponents: [SectionComponent, SectionUpdateComponent, SectionDeleteDialogComponent, SectionDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationSectionModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
