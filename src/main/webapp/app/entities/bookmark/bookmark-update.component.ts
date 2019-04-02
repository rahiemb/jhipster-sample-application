import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBookmark } from 'app/shared/model/bookmark.model';
import { BookmarkService } from './bookmark.service';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from 'app/entities/users';

@Component({
    selector: 'jhi-bookmark-update',
    templateUrl: './bookmark-update.component.html'
})
export class BookmarkUpdateComponent implements OnInit {
    bookmark: IBookmark;
    isSaving: boolean;

    users: IUsers[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected bookmarkService: BookmarkService,
        protected usersService: UsersService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bookmark }) => {
            this.bookmark = bookmark;
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
        if (this.bookmark.id !== undefined) {
            this.subscribeToSaveResponse(this.bookmarkService.update(this.bookmark));
        } else {
            this.subscribeToSaveResponse(this.bookmarkService.create(this.bookmark));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookmark>>) {
        result.subscribe((res: HttpResponse<IBookmark>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
