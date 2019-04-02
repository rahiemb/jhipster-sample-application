import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from './action.service';
import { IUser, UserService } from 'app/core';
import { IVersion } from 'app/shared/model/version.model';
import { VersionService } from 'app/entities/version';
import { IStep } from 'app/shared/model/step.model';
import { StepService } from 'app/entities/step';

@Component({
    selector: 'jhi-action-update',
    templateUrl: './action-update.component.html'
})
export class ActionUpdateComponent implements OnInit {
    action: IAction;
    isSaving: boolean;

    users: IUser[];

    versions: IVersion[];

    steps: IStep[];
    originationTimestamp: string;
    responseTimestamp: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected actionService: ActionService,
        protected userService: UserService,
        protected versionService: VersionService,
        protected stepService: StepService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ action }) => {
            this.action = action;
            this.originationTimestamp =
                this.action.originationTimestamp != null ? this.action.originationTimestamp.format(DATE_TIME_FORMAT) : null;
            this.responseTimestamp = this.action.responseTimestamp != null ? this.action.responseTimestamp.format(DATE_TIME_FORMAT) : null;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.versionService
            .query({ filter: 'action-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IVersion[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVersion[]>) => response.body)
            )
            .subscribe(
                (res: IVersion[]) => {
                    if (!this.action.version || !this.action.version.id) {
                        this.versions = res;
                    } else {
                        this.versionService
                            .find(this.action.version.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IVersion>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IVersion>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IVersion) => (this.versions = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.stepService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStep[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStep[]>) => response.body)
            )
            .subscribe((res: IStep[]) => (this.steps = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.action.originationTimestamp = this.originationTimestamp != null ? moment(this.originationTimestamp, DATE_TIME_FORMAT) : null;
        this.action.responseTimestamp = this.responseTimestamp != null ? moment(this.responseTimestamp, DATE_TIME_FORMAT) : null;
        if (this.action.id !== undefined) {
            this.subscribeToSaveResponse(this.actionService.update(this.action));
        } else {
            this.subscribeToSaveResponse(this.actionService.create(this.action));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAction>>) {
        result.subscribe((res: HttpResponse<IAction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVersionById(index: number, item: IVersion) {
        return item.id;
    }

    trackStepById(index: number, item: IStep) {
        return item.id;
    }
}
