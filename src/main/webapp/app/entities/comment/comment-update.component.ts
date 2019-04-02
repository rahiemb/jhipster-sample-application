import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from 'app/entities/users';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document';
import { IStep } from 'app/shared/model/step.model';
import { StepService } from 'app/entities/step';

@Component({
    selector: 'jhi-comment-update',
    templateUrl: './comment-update.component.html'
})
export class CommentUpdateComponent implements OnInit {
    comment: IComment;
    isSaving: boolean;

    replytos: IComment[];

    users: IUsers[];

    comments: IComment[];

    documents: IDocument[];

    steps: IStep[];

    users: IUsers[];
    timestamp: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commentService: CommentService,
        protected usersService: UsersService,
        protected documentService: DocumentService,
        protected stepService: StepService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ comment }) => {
            this.comment = comment;
            this.timestamp = this.comment.timestamp != null ? this.comment.timestamp.format(DATE_TIME_FORMAT) : null;
        });
        this.commentService
            .query({ filter: 'comment-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IComment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IComment[]>) => response.body)
            )
            .subscribe(
                (res: IComment[]) => {
                    if (!this.comment.replyTo || !this.comment.replyTo.id) {
                        this.replytos = res;
                    } else {
                        this.commentService
                            .find(this.comment.replyTo.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IComment>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IComment>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IComment) => (this.replytos = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.usersService
            .query({ filter: 'comment-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IUsers[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUsers[]>) => response.body)
            )
            .subscribe(
                (res: IUsers[]) => {
                    if (!this.comment.user || !this.comment.user.id) {
                        this.users = res;
                    } else {
                        this.usersService
                            .find(this.comment.user.id)
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
        this.commentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IComment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IComment[]>) => response.body)
            )
            .subscribe((res: IComment[]) => (this.comments = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.documentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDocument[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDocument[]>) => response.body)
            )
            .subscribe((res: IDocument[]) => (this.documents = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.stepService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStep[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStep[]>) => response.body)
            )
            .subscribe((res: IStep[]) => (this.steps = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.comment.timestamp = this.timestamp != null ? moment(this.timestamp, DATE_TIME_FORMAT) : null;
        if (this.comment.id !== undefined) {
            this.subscribeToSaveResponse(this.commentService.update(this.comment));
        } else {
            this.subscribeToSaveResponse(this.commentService.create(this.comment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>) {
        result.subscribe((res: HttpResponse<IComment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCommentById(index: number, item: IComment) {
        return item.id;
    }

    trackUsersById(index: number, item: IUsers) {
        return item.id;
    }

    trackDocumentById(index: number, item: IDocument) {
        return item.id;
    }

    trackStepById(index: number, item: IStep) {
        return item.id;
    }
}
