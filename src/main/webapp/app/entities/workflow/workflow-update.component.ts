import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IWorkflow } from 'app/shared/model/workflow.model';
import { WorkflowService } from './workflow.service';
import { IVersion } from 'app/shared/model/version.model';
import { VersionService } from 'app/entities/version';

@Component({
    selector: 'jhi-workflow-update',
    templateUrl: './workflow-update.component.html'
})
export class WorkflowUpdateComponent implements OnInit {
    workflow: IWorkflow;
    isSaving: boolean;

    versions: IVersion[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected workflowService: WorkflowService,
        protected versionService: VersionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ workflow }) => {
            this.workflow = workflow;
        });
        this.versionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVersion[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVersion[]>) => response.body)
            )
            .subscribe((res: IVersion[]) => (this.versions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.workflow.id !== undefined) {
            this.subscribeToSaveResponse(this.workflowService.update(this.workflow));
        } else {
            this.subscribeToSaveResponse(this.workflowService.create(this.workflow));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkflow>>) {
        result.subscribe((res: HttpResponse<IWorkflow>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVersionById(index: number, item: IVersion) {
        return item.id;
    }
}
