import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    WorkflowComponent,
    WorkflowDetailComponent,
    WorkflowUpdateComponent,
    WorkflowDeletePopupComponent,
    WorkflowDeleteDialogComponent,
    workflowRoute,
    workflowPopupRoute
} from './';

const ENTITY_STATES = [...workflowRoute, ...workflowPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WorkflowComponent,
        WorkflowDetailComponent,
        WorkflowUpdateComponent,
        WorkflowDeleteDialogComponent,
        WorkflowDeletePopupComponent
    ],
    entryComponents: [WorkflowComponent, WorkflowUpdateComponent, WorkflowDeleteDialogComponent, WorkflowDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationWorkflowModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
