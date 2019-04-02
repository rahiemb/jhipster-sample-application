import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITitle } from 'app/shared/model/title.model';
import { AccountService } from 'app/core';
import { TitleService } from './title.service';

@Component({
    selector: 'jhi-title',
    templateUrl: './title.component.html'
})
export class TitleComponent implements OnInit, OnDestroy {
    titles: ITitle[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected titleService: TitleService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.titleService
            .query()
            .pipe(
                filter((res: HttpResponse<ITitle[]>) => res.ok),
                map((res: HttpResponse<ITitle[]>) => res.body)
            )
            .subscribe(
                (res: ITitle[]) => {
                    this.titles = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTitles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITitle) {
        return item.id;
    }

    registerChangeInTitles() {
        this.eventSubscriber = this.eventManager.subscribe('titleListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
