import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    LockComponent,
    LockDetailComponent,
    LockUpdateComponent,
    LockDeletePopupComponent,
    LockDeleteDialogComponent,
    lockRoute,
    lockPopupRoute
} from './';

const ENTITY_STATES = [...lockRoute, ...lockPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LockComponent, LockDetailComponent, LockUpdateComponent, LockDeleteDialogComponent, LockDeletePopupComponent],
    entryComponents: [LockComponent, LockUpdateComponent, LockDeleteDialogComponent, LockDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationLockModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
