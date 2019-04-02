import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from './document.service';
import { ILock } from 'app/shared/model/lock.model';
import { LockService } from 'app/entities/lock';
import { IWorkflow } from 'app/shared/model/workflow.model';
import { WorkflowService } from 'app/entities/workflow';
import { IKeyword } from 'app/shared/model/keyword.model';
import { KeywordService } from 'app/entities/keyword';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { IParticipant } from 'app/shared/model/participant.model';
import { ParticipantService } from 'app/entities/participant';
import { ISection } from 'app/shared/model/section.model';
import { SectionService } from 'app/entities/section';

@Component({
    selector: 'jhi-document-update',
    templateUrl: './document-update.component.html'
})
export class DocumentUpdateComponent implements OnInit {
    document: IDocument;
    isSaving: boolean;

    locks: ILock[];

    workflows: IWorkflow[];

    keywords: IKeyword[];

    categories: ICategory[];

    participants: IParticipant[];

    sections: ISection[];
    effectiveDateDp: any;
    approvalDateDp: any;
    supersedesDateDp: any;
    originalDateDp: any;
    reviewDateDp: any;
    revisionDateDp: any;
    expirationDateDp: any;
    retiredDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected documentService: DocumentService,
        protected lockService: LockService,
        protected workflowService: WorkflowService,
        protected keywordService: KeywordService,
        protected categoryService: CategoryService,
        protected participantService: ParticipantService,
        protected sectionService: SectionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ document }) => {
            this.document = document;
        });
        this.lockService
            .query({ filter: 'document-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ILock[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILock[]>) => response.body)
            )
            .subscribe(
                (res: ILock[]) => {
                    if (!this.document.lock || !this.document.lock.id) {
                        this.locks = res;
                    } else {
                        this.lockService
                            .find(this.document.lock.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ILock>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ILock>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ILock) => (this.locks = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.workflowService
            .query({ filter: 'document-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IWorkflow[]>) => mayBeOk.ok),
                map((response: HttpResponse<IWorkflow[]>) => response.body)
            )
            .subscribe(
                (res: IWorkflow[]) => {
                    if (!this.document.workflow || !this.document.workflow.id) {
                        this.workflows = res;
                    } else {
                        this.workflowService
                            .find(this.document.workflow.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IWorkflow>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IWorkflow>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IWorkflow) => (this.workflows = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.keywordService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IKeyword[]>) => mayBeOk.ok),
                map((response: HttpResponse<IKeyword[]>) => response.body)
            )
            .subscribe((res: IKeyword[]) => (this.keywords = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.categoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICategory[]>) => response.body)
            )
            .subscribe((res: ICategory[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.participantService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IParticipant[]>) => mayBeOk.ok),
                map((response: HttpResponse<IParticipant[]>) => response.body)
            )
            .subscribe((res: IParticipant[]) => (this.participants = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.sectionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISection[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISection[]>) => response.body)
            )
            .subscribe((res: ISection[]) => (this.sections = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.document.id !== undefined) {
            this.subscribeToSaveResponse(this.documentService.update(this.document));
        } else {
            this.subscribeToSaveResponse(this.documentService.create(this.document));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocument>>) {
        result.subscribe((res: HttpResponse<IDocument>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLockById(index: number, item: ILock) {
        return item.id;
    }

    trackWorkflowById(index: number, item: IWorkflow) {
        return item.id;
    }

    trackKeywordById(index: number, item: IKeyword) {
        return item.id;
    }

    trackCategoryById(index: number, item: ICategory) {
        return item.id;
    }

    trackParticipantById(index: number, item: IParticipant) {
        return item.id;
    }

    trackSectionById(index: number, item: ISection) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
