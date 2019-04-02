import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILock } from 'app/shared/model/lock.model';
import { AccountService } from 'app/core';
import { LockService } from './lock.service';

@Component({
    selector: 'jhi-lock',
    templateUrl: './lock.component.html'
})
export class LockComponent implements OnInit, OnDestroy {
    locks: ILock[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected lockService: LockService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.lockService
            .query()
            .pipe(
                filter((res: HttpResponse<ILock[]>) => res.ok),
                map((res: HttpResponse<ILock[]>) => res.body)
            )
            .subscribe(
                (res: ILock[]) => {
                    this.locks = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLocks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILock) {
        return item.id;
    }

    registerChangeInLocks() {
        this.eventSubscriber = this.eventManager.subscribe('lockListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
