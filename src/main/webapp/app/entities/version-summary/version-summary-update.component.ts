import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IVersionSummary } from 'app/shared/model/version-summary.model';
import { VersionSummaryService } from './version-summary.service';

@Component({
    selector: 'jhi-version-summary-update',
    templateUrl: './version-summary-update.component.html'
})
export class VersionSummaryUpdateComponent implements OnInit {
    versionSummary: IVersionSummary;
    isSaving: boolean;

    constructor(protected versionSummaryService: VersionSummaryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ versionSummary }) => {
            this.versionSummary = versionSummary;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.versionSummary.id !== undefined) {
            this.subscribeToSaveResponse(this.versionSummaryService.update(this.versionSummary));
        } else {
            this.subscribeToSaveResponse(this.versionSummaryService.create(this.versionSummary));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVersionSummary>>) {
        result.subscribe((res: HttpResponse<IVersionSummary>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
