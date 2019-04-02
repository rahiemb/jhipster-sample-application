import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    UserGroupComponent,
    UserGroupDetailComponent,
    UserGroupUpdateComponent,
    UserGroupDeletePopupComponent,
    UserGroupDeleteDialogComponent,
    userGroupRoute,
    userGroupPopupRoute
} from './';

const ENTITY_STATES = [...userGroupRoute, ...userGroupPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        UserGroupComponent,
        UserGroupDetailComponent,
        UserGroupUpdateComponent,
        UserGroupDeleteDialogComponent,
        UserGroupDeletePopupComponent
    ],
    entryComponents: [UserGroupComponent, UserGroupUpdateComponent, UserGroupDeleteDialogComponent, UserGroupDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationUserGroupModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
