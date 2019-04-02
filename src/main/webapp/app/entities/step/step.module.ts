import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    StepComponent,
    StepDetailComponent,
    StepUpdateComponent,
    StepDeletePopupComponent,
    StepDeleteDialogComponent,
    stepRoute,
    stepPopupRoute
} from './';

const ENTITY_STATES = [...stepRoute, ...stepPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [StepComponent, StepDetailComponent, StepUpdateComponent, StepDeleteDialogComponent, StepDeletePopupComponent],
    entryComponents: [StepComponent, StepUpdateComponent, StepDeleteDialogComponent, StepDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationStepModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
