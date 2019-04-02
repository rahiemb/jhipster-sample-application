import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IManualType } from 'app/shared/model/manual-type.model';
import { AccountService } from 'app/core';
import { ManualTypeService } from './manual-type.service';

@Component({
    selector: 'jhi-manual-type',
    templateUrl: './manual-type.component.html'
})
export class ManualTypeComponent implements OnInit, OnDestroy {
    manualTypes: IManualType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected manualTypeService: ManualTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.manualTypeService
            .query()
            .pipe(
                filter((res: HttpResponse<IManualType[]>) => res.ok),
                map((res: HttpResponse<IManualType[]>) => res.body)
            )
            .subscribe(
                (res: IManualType[]) => {
                    this.manualTypes = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInManualTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IManualType) {
        return item.id;
    }

    registerChangeInManualTypes() {
        this.eventSubscriber = this.eventManager.subscribe('manualTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
