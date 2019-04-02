import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    DocumentTemplateComponent,
    DocumentTemplateDetailComponent,
    DocumentTemplateUpdateComponent,
    DocumentTemplateDeletePopupComponent,
    DocumentTemplateDeleteDialogComponent,
    documentTemplateRoute,
    documentTemplatePopupRoute
} from './';

const ENTITY_STATES = [...documentTemplateRoute, ...documentTemplatePopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DocumentTemplateComponent,
        DocumentTemplateDetailComponent,
        DocumentTemplateUpdateComponent,
        DocumentTemplateDeleteDialogComponent,
        DocumentTemplateDeletePopupComponent
    ],
    entryComponents: [
        DocumentTemplateComponent,
        DocumentTemplateUpdateComponent,
        DocumentTemplateDeleteDialogComponent,
        DocumentTemplateDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationDocumentTemplateModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
