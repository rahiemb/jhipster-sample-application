import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRecipient } from 'app/shared/model/recipient.model';
import { RecipientService } from './recipient.service';
import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from 'app/entities/notification';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from 'app/entities/users';

@Component({
    selector: 'jhi-recipient-update',
    templateUrl: './recipient-update.component.html'
})
export class RecipientUpdateComponent implements OnInit {
    recipient: IRecipient;
    isSaving: boolean;

    notifications: INotification[];

    users: IUsers[];
    timestampDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected recipientService: RecipientService,
        protected notificationService: NotificationService,
        protected usersService: UsersService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recipient }) => {
            this.recipient = recipient;
        });
        this.notificationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<INotification[]>) => mayBeOk.ok),
                map((response: HttpResponse<INotification[]>) => response.body)
            )
            .subscribe((res: INotification[]) => (this.notifications = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.usersService
            .query({ filter: 'recipient-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IUsers[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUsers[]>) => response.body)
            )
            .subscribe(
                (res: IUsers[]) => {
                    if (!this.recipient.user || !this.recipient.user.id) {
                        this.users = res;
                    } else {
                        this.usersService
                            .find(this.recipient.user.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IUsers>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IUsers>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IUsers) => (this.users = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recipient.id !== undefined) {
            this.subscribeToSaveResponse(this.recipientService.update(this.recipient));
        } else {
            this.subscribeToSaveResponse(this.recipientService.create(this.recipient));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecipient>>) {
        result.subscribe((res: HttpResponse<IRecipient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackNotificationById(index: number, item: INotification) {
        return item.id;
    }

    trackUsersById(index: number, item: IUsers) {
        return item.id;
    }
}
