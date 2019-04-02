import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    AbilityComponent,
    AbilityDetailComponent,
    AbilityUpdateComponent,
    AbilityDeletePopupComponent,
    AbilityDeleteDialogComponent,
    abilityRoute,
    abilityPopupRoute
} from './';

const ENTITY_STATES = [...abilityRoute, ...abilityPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AbilityComponent,
        AbilityDetailComponent,
        AbilityUpdateComponent,
        AbilityDeleteDialogComponent,
        AbilityDeletePopupComponent
    ],
    entryComponents: [AbilityComponent, AbilityUpdateComponent, AbilityDeleteDialogComponent, AbilityDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationAbilityModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
