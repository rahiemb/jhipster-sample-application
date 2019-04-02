import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVersion } from 'app/shared/model/version.model';
import { VersionService } from './version.service';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document';
import { IVersionSummary } from 'app/shared/model/version-summary.model';
import { VersionSummaryService } from 'app/entities/version-summary';

@Component({
    selector: 'jhi-version-update',
    templateUrl: './version-update.component.html'
})
export class VersionUpdateComponent implements OnInit {
    version: IVersion;
    isSaving: boolean;

    documents: IDocument[];

    summaries: IVersionSummary[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected versionService: VersionService,
        protected documentService: DocumentService,
        protected versionSummaryService: VersionSummaryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ version }) => {
            this.version = version;
        });
        this.documentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDocument[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDocument[]>) => response.body)
            )
            .subscribe((res: IDocument[]) => (this.documents = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.versionSummaryService
            .query({ filter: 'version-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IVersionSummary[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVersionSummary[]>) => response.body)
            )
            .subscribe(
                (res: IVersionSummary[]) => {
                    if (!this.version.summary || !this.version.summary.id) {
                        this.summaries = res;
                    } else {
                        this.versionSummaryService
                            .find(this.version.summary.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IVersionSummary>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IVersionSummary>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IVersionSummary) => (this.summaries = [subRes].concat(res)),
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
        if (this.version.id !== undefined) {
            this.subscribeToSaveResponse(this.versionService.update(this.version));
        } else {
            this.subscribeToSaveResponse(this.versionService.create(this.version));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVersion>>) {
        result.subscribe((res: HttpResponse<IVersion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDocumentById(index: number, item: IDocument) {
        return item.id;
    }

    trackVersionSummaryById(index: number, item: IVersionSummary) {
        return item.id;
    }
}
