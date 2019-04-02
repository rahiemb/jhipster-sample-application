import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVersionSummary } from 'app/shared/model/version-summary.model';
import { AccountService } from 'app/core';
import { VersionSummaryService } from './version-summary.service';

@Component({
    selector: 'jhi-version-summary',
    templateUrl: './version-summary.component.html'
})
export class VersionSummaryComponent implements OnInit, OnDestroy {
    versionSummaries: IVersionSummary[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected versionSummaryService: VersionSummaryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.versionSummaryService
            .query()
            .pipe(
                filter((res: HttpResponse<IVersionSummary[]>) => res.ok),
                map((res: HttpResponse<IVersionSummary[]>) => res.body)
            )
            .subscribe(
                (res: IVersionSummary[]) => {
                    this.versionSummaries = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVersionSummaries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVersionSummary) {
        return item.id;
    }

    registerChangeInVersionSummaries() {
        this.eventSubscriber = this.eventManager.subscribe('versionSummaryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
