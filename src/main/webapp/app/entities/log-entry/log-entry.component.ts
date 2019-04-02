import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILogEntry } from 'app/shared/model/log-entry.model';
import { AccountService } from 'app/core';
import { LogEntryService } from './log-entry.service';

@Component({
    selector: 'jhi-log-entry',
    templateUrl: './log-entry.component.html'
})
export class LogEntryComponent implements OnInit, OnDestroy {
    logEntries: ILogEntry[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected logEntryService: LogEntryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.logEntryService
            .query()
            .pipe(
                filter((res: HttpResponse<ILogEntry[]>) => res.ok),
                map((res: HttpResponse<ILogEntry[]>) => res.body)
            )
            .subscribe(
                (res: ILogEntry[]) => {
                    this.logEntries = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLogEntries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILogEntry) {
        return item.id;
    }

    registerChangeInLogEntries() {
        this.eventSubscriber = this.eventManager.subscribe('logEntryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
