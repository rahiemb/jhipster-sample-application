import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPart } from 'app/shared/model/part.model';
import { AccountService } from 'app/core';
import { PartService } from './part.service';

@Component({
    selector: 'jhi-part',
    templateUrl: './part.component.html'
})
export class PartComponent implements OnInit, OnDestroy {
    parts: IPart[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected partService: PartService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.partService
            .query()
            .pipe(
                filter((res: HttpResponse<IPart[]>) => res.ok),
                map((res: HttpResponse<IPart[]>) => res.body)
            )
            .subscribe(
                (res: IPart[]) => {
                    this.parts = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInParts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPart) {
        return item.id;
    }

    registerChangeInParts() {
        this.eventSubscriber = this.eventManager.subscribe('partListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
