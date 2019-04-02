import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITitle } from 'app/shared/model/title.model';
import { TitleService } from './title.service';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from 'app/entities/users';

@Component({
    selector: 'jhi-title-update',
    templateUrl: './title-update.component.html'
})
export class TitleUpdateComponent implements OnInit {
    title: ITitle;
    isSaving: boolean;

    users: IUsers[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected titleService: TitleService,
        protected usersService: UsersService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ title }) => {
            this.title = title;
        });
        this.usersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUsers[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUsers[]>) => response.body)
            )
            .subscribe((res: IUsers[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.title.id !== undefined) {
            this.subscribeToSaveResponse(this.titleService.update(this.title));
        } else {
            this.subscribeToSaveResponse(this.titleService.create(this.title));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITitle>>) {
        result.subscribe((res: HttpResponse<ITitle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
