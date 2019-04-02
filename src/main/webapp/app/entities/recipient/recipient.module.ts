import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
    RecipientComponent,
    RecipientDetailComponent,
    RecipientUpdateComponent,
    RecipientDeletePopupComponent,
    RecipientDeleteDialogComponent,
    recipientRoute,
    recipientPopupRoute
} from './';

const ENTITY_STATES = [...recipientRoute, ...recipientPopupRoute];

@NgModule({
    imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecipientComponent,
        RecipientDetailComponent,
        RecipientUpdateComponent,
        RecipientDeleteDialogComponent,
        RecipientDeletePopupComponent
    ],
    entryComponents: [RecipientComponent, RecipientUpdateComponent, RecipientDeleteDialogComponent, RecipientDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationRecipientModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
