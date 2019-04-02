import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDocumentTemplatePart } from 'app/shared/model/document-template-part.model';
import { DocumentTemplatePartService } from './document-template-part.service';
import { IDocumentTemplate } from 'app/shared/model/document-template.model';
import { DocumentTemplateService } from 'app/entities/document-template';

@Component({
    selector: 'jhi-document-template-part-update',
    templateUrl: './document-template-part-update.component.html'
})
export class DocumentTemplatePartUpdateComponent implements OnInit {
    documentTemplatePart: IDocumentTemplatePart;
    isSaving: boolean;

    documenttemplates: IDocumentTemplate[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected documentTemplatePartService: DocumentTemplatePartService,
        protected documentTemplateService: DocumentTemplateService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ documentTemplatePart }) => {
            this.documentTemplatePart = documentTemplatePart;
        });
        this.documentTemplateService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDocumentTemplate[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDocumentTemplate[]>) => response.body)
            )
            .subscribe((res: IDocumentTemplate[]) => (this.documenttemplates = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.documentTemplatePart.id !== undefined) {
            this.subscribeToSaveResponse(this.documentTemplatePartService.update(this.documentTemplatePart));
        } else {
            this.subscribeToSaveResponse(this.documentTemplatePartService.create(this.documentTemplatePart));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentTemplatePart>>) {
        result.subscribe(
            (res: HttpResponse<IDocumentTemplatePart>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackDocumentTemplateById(index: number, item: IDocumentTemplate) {
        return item.id;
    }
}
