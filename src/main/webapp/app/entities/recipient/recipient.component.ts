import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRecipient } from 'app/shared/model/recipient.model';
import { AccountService } from 'app/core';
import { RecipientService } from './recipient.service';

@Component({
    selector: 'jhi-recipient',
    templateUrl: './recipient.component.html'
})
export class RecipientComponent implements OnInit, OnDestroy {
    recipients: IRecipient[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected recipientService: RecipientService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.recipientService
            .query()
            .pipe(
                filter((res: HttpResponse<IRecipient[]>) => res.ok),
                map((res: HttpResponse<IRecipient[]>) => res.body)
            )
            .subscribe(
                (res: IRecipient[]) => {
                    this.recipients = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRecipients();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRecipient) {
        return item.id;
    }

    registerChangeInRecipients() {
        this.eventSubscriber = this.eventManager.subscribe('recipientListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
