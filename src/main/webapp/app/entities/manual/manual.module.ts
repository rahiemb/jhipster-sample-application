import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    ManualComponent,
    ManualDetailComponent,
    ManualUpdateComponent,
    ManualDeletePopupComponent,
    ManualDeleteDialogComponent,
    manualRoute,
    manualPopupRoute
} from './';

const ENTITY_STATES = [...manualRoute, ...manualPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ManualComponent, ManualDetailComponent, ManualUpdateComponent, ManualDeleteDialogComponent, ManualDeletePopupComponent],
    entryComponents: [ManualComponent, ManualUpdateComponent, ManualDeleteDialogComponent, ManualDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationManualModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
