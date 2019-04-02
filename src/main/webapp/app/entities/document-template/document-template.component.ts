import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDocumentTemplate } from 'app/shared/model/document-template.model';
import { AccountService } from 'app/core';
import { DocumentTemplateService } from './document-template.service';

@Component({
    selector: 'jhi-document-template',
    templateUrl: './document-template.component.html'
})
export class DocumentTemplateComponent implements OnInit, OnDestroy {
    documentTemplates: IDocumentTemplate[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected documentTemplateService: DocumentTemplateService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.documentTemplateService
            .query()
            .pipe(
                filter((res: HttpResponse<IDocumentTemplate[]>) => res.ok),
                map((res: HttpResponse<IDocumentTemplate[]>) => res.body)
            )
            .subscribe(
                (res: IDocumentTemplate[]) => {
                    this.documentTemplates = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDocumentTemplates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDocumentTemplate) {
        return item.id;
    }

    registerChangeInDocumentTemplates() {
        this.eventSubscriber = this.eventManager.subscribe('documentTemplateListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
