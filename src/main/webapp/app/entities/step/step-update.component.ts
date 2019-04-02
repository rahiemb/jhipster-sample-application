import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStep } from 'app/shared/model/step.model';
import { StepService } from './step.service';
import { IUser, UserService } from 'app/core';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from 'app/entities/users';
import { IWorkflow } from 'app/shared/model/workflow.model';
import { WorkflowService } from 'app/entities/workflow';

@Component({
    selector: 'jhi-step-update',
    templateUrl: './step-update.component.html'
})
export class StepUpdateComponent implements OnInit {
    step: IStep;
    isSaving: boolean;

    users: IUser[];

    users: IUsers[];

    workflows: IWorkflow[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stepService: StepService,
        protected userService: UserService,
        protected usersService: UsersService,
        protected workflowService: WorkflowService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ step }) => {
            this.step = step;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.usersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUsers[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUsers[]>) => response.body)
            )
            .subscribe((res: IUsers[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.workflowService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IWorkflow[]>) => mayBeOk.ok),
                map((response: HttpResponse<IWorkflow[]>) => response.body)
            )
            .subscribe((res: IWorkflow[]) => (this.workflows = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.step.id !== undefined) {
            this.subscribeToSaveResponse(this.stepService.update(this.step));
        } else {
            this.subscribeToSaveResponse(this.stepService.create(this.step));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStep>>) {
        result.subscribe((res: HttpResponse<IStep>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackUsersById(index: number, item: IUsers) {
        return item.id;
    }

    trackWorkflowById(index: number, item: IWorkflow) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
