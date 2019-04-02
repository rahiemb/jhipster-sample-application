import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDocumentTemplate } from 'app/shared/model/document-template.model';
import { DocumentTemplateService } from './document-template.service';
import { IManual } from 'app/shared/model/manual.model';
import { ManualService } from 'app/entities/manual';

@Component({
    selector: 'jhi-document-template-update',
    templateUrl: './document-template-update.component.html'
})
export class DocumentTemplateUpdateComponent implements OnInit {
    documentTemplate: IDocumentTemplate;
    isSaving: boolean;

    manuals: IManual[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected documentTemplateService: DocumentTemplateService,
        protected manualService: ManualService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ documentTemplate }) => {
            this.documentTemplate = documentTemplate;
        });
        this.manualService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IManual[]>) => mayBeOk.ok),
                map((response: HttpResponse<IManual[]>) => response.body)
            )
            .subscribe((res: IManual[]) => (this.manuals = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.documentTemplate.id !== undefined) {
            this.subscribeToSaveResponse(this.documentTemplateService.update(this.documentTemplate));
        } else {
            this.subscribeToSaveResponse(this.documentTemplateService.create(this.documentTemplate));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentTemplate>>) {
        result.subscribe((res: HttpResponse<IDocumentTemplate>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackManualById(index: number, item: IManual) {
        return item.id;
    }
}
