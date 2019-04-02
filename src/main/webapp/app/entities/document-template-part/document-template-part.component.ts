import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDocumentTemplatePart } from 'app/shared/model/document-template-part.model';
import { AccountService } from 'app/core';
import { DocumentTemplatePartService } from './document-template-part.service';

@Component({
    selector: 'jhi-document-template-part',
    templateUrl: './document-template-part.component.html'
})
export class DocumentTemplatePartComponent implements OnInit, OnDestroy {
    documentTemplateParts: IDocumentTemplatePart[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected documentTemplatePartService: DocumentTemplatePartService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.documentTemplatePartService
            .query()
            .pipe(
                filter((res: HttpResponse<IDocumentTemplatePart[]>) => res.ok),
                map((res: HttpResponse<IDocumentTemplatePart[]>) => res.body)
            )
            .subscribe(
                (res: IDocumentTemplatePart[]) => {
                    this.documentTemplateParts = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDocumentTemplateParts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDocumentTemplatePart) {
        return item.id;
    }

    registerChangeInDocumentTemplateParts() {
        this.eventSubscriber = this.eventManager.subscribe('documentTemplatePartListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
