import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from './notification.service';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from 'app/entities/users';
import { IStep } from 'app/shared/model/step.model';
import { StepService } from 'app/entities/step';

@Component({
    selector: 'jhi-notification-update',
    templateUrl: './notification-update.component.html'
})
export class NotificationUpdateComponent implements OnInit {
    notification: INotification;
    isSaving: boolean;

    senders: IUsers[];

    steps: IStep[];
    beforeDateDp: any;
    afterDateDp: any;
    sendDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected notificationService: NotificationService,
        protected usersService: UsersService,
        protected stepService: StepService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ notification }) => {
            this.notification = notification;
        });
        this.usersService
            .query({ filter: 'notification-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IUsers[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUsers[]>) => response.body)
            )
            .subscribe(
                (res: IUsers[]) => {
                    if (!this.notification.sender || !this.notification.sender.id) {
                        this.senders = res;
                    } else {
                        this.usersService
                            .find(this.notification.sender.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IUsers>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IUsers>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IUsers) => (this.senders = [subRes].concat(res)),
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
        if (this.notification.id !== undefined) {
            this.subscribeToSaveResponse(this.notificationService.update(this.notification));
        } else {
            this.subscribeToSaveResponse(this.notificationService.create(this.notification));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<INotification>>) {
        result.subscribe((res: HttpResponse<INotification>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUsersById(index: number, item: IUsers) {
        return item.id;
    }

    trackStepById(index: number, item: IStep) {
        return item.id;
    }
}
