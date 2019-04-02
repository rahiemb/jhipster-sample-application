import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEmail } from 'app/shared/model/email.model';
import { EmailService } from './email.service';
import { IStep } from 'app/shared/model/step.model';
import { StepService } from 'app/entities/step';

@Component({
    selector: 'jhi-email-update',
    templateUrl: './email-update.component.html'
})
export class EmailUpdateComponent implements OnInit {
    email: IEmail;
    isSaving: boolean;

    steps: IStep[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected emailService: EmailService,
        protected stepService: StepService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ email }) => {
            this.email = email;
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
        if (this.email.id !== undefined) {
            this.subscribeToSaveResponse(this.emailService.update(this.email));
        } else {
            this.subscribeToSaveResponse(this.emailService.create(this.email));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmail>>) {
        result.subscribe((res: HttpResponse<IEmail>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
