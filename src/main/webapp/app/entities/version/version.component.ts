import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVersion } from 'app/shared/model/version.model';
import { AccountService } from 'app/core';
import { VersionService } from './version.service';

@Component({
    selector: 'jhi-version',
    templateUrl: './version.component.html'
})
export class VersionComponent implements OnInit, OnDestroy {
    versions: IVersion[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected versionService: VersionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.versionService
            .query()
            .pipe(
                filter((res: HttpResponse<IVersion[]>) => res.ok),
                map((res: HttpResponse<IVersion[]>) => res.body)
            )
            .subscribe(
                (res: IVersion[]) => {
                    this.versions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVersions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVersion) {
        return item.id;
    }

    registerChangeInVersions() {
        this.eventSubscriber = this.eventManager.subscribe('versionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
