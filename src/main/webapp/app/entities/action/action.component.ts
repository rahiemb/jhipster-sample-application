import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAction } from 'app/shared/model/action.model';
import { AccountService } from 'app/core';
import { ActionService } from './action.service';

@Component({
    selector: 'jhi-action',
    templateUrl: './action.component.html'
})
export class ActionComponent implements OnInit, OnDestroy {
    actions: IAction[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected actionService: ActionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.actionService
            .query()
            .pipe(
                filter((res: HttpResponse<IAction[]>) => res.ok),
                map((res: HttpResponse<IAction[]>) => res.body)
            )
            .subscribe(
                (res: IAction[]) => {
                    this.actions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInActions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAction) {
        return item.id;
    }

    registerChangeInActions() {
        this.eventSubscriber = this.eventManager.subscribe('actionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
