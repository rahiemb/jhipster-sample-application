import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IManual } from 'app/shared/model/manual.model';
import { AccountService } from 'app/core';
import { ManualService } from './manual.service';

@Component({
    selector: 'jhi-manual',
    templateUrl: './manual.component.html'
})
export class ManualComponent implements OnInit, OnDestroy {
    manuals: IManual[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected manualService: ManualService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.manualService
            .query()
            .pipe(
                filter((res: HttpResponse<IManual[]>) => res.ok),
                map((res: HttpResponse<IManual[]>) => res.body)
            )
            .subscribe(
                (res: IManual[]) => {
                    this.manuals = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInManuals();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IManual) {
        return item.id;
    }

    registerChangeInManuals() {
        this.eventSubscriber = this.eventManager.subscribe('manualListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
