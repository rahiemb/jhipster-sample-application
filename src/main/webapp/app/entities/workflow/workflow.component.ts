import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IWorkflow } from 'app/shared/model/workflow.model';
import { AccountService } from 'app/core';
import { WorkflowService } from './workflow.service';

@Component({
    selector: 'jhi-workflow',
    templateUrl: './workflow.component.html'
})
export class WorkflowComponent implements OnInit, OnDestroy {
    workflows: IWorkflow[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected workflowService: WorkflowService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.workflowService
            .query()
            .pipe(
                filter((res: HttpResponse<IWorkflow[]>) => res.ok),
                map((res: HttpResponse<IWorkflow[]>) => res.body)
            )
            .subscribe(
                (res: IWorkflow[]) => {
                    this.workflows = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInWorkflows();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IWorkflow) {
        return item.id;
    }

    registerChangeInWorkflows() {
        this.eventSubscriber = this.eventManager.subscribe('workflowListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
