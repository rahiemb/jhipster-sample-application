import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    DocumentTemplatePartComponent,
    DocumentTemplatePartDetailComponent,
    DocumentTemplatePartUpdateComponent,
    DocumentTemplatePartDeletePopupComponent,
    DocumentTemplatePartDeleteDialogComponent,
    documentTemplatePartRoute,
    documentTemplatePartPopupRoute
} from './';

const ENTITY_STATES = [...documentTemplatePartRoute, ...documentTemplatePartPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DocumentTemplatePartComponent,
        DocumentTemplatePartDetailComponent,
        DocumentTemplatePartUpdateComponent,
        DocumentTemplatePartDeleteDialogComponent,
        DocumentTemplatePartDeletePopupComponent
    ],
    entryComponents: [
        DocumentTemplatePartComponent,
        DocumentTemplatePartUpdateComponent,
        DocumentTemplatePartDeleteDialogComponent,
        DocumentTemplatePartDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationDocumentTemplatePartModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
