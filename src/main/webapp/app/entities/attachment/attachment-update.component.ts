import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAttachment } from 'app/shared/model/attachment.model';
import { AttachmentService } from './attachment.service';
import { IStep } from 'app/shared/model/step.model';
import { StepService } from 'app/entities/step';

@Component({
    selector: 'jhi-attachment-update',
    templateUrl: './attachment-update.component.html'
})
export class AttachmentUpdateComponent implements OnInit {
    attachment: IAttachment;
    isSaving: boolean;

    steps: IStep[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected attachmentService: AttachmentService,
        protected stepService: StepService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ attachment }) => {
            this.attachment = attachment;
        });
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
        if (this.attachment.id !== undefined) {
            this.subscribeToSaveResponse(this.attachmentService.update(this.attachment));
        } else {
            this.subscribeToSaveResponse(this.attachmentService.create(this.attachment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttachment>>) {
        result.subscribe((res: HttpResponse<IAttachment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackStepById(index: number, item: IStep) {
        return item.id;
    }
}
