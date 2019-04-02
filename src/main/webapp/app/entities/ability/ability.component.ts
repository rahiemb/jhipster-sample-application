import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAbility } from 'app/shared/model/ability.model';
import { AccountService } from 'app/core';
import { AbilityService } from './ability.service';

@Component({
    selector: 'jhi-ability',
    templateUrl: './ability.component.html'
})
export class AbilityComponent implements OnInit, OnDestroy {
    abilities: IAbility[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected abilityService: AbilityService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.abilityService
            .query()
            .pipe(
                filter((res: HttpResponse<IAbility[]>) => res.ok),
                map((res: HttpResponse<IAbility[]>) => res.body)
            )
            .subscribe(
                (res: IAbility[]) => {
                    this.abilities = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAbilities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAbility) {
        return item.id;
    }

    registerChangeInAbilities() {
        this.eventSubscriber = this.eventManager.subscribe('abilityListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
