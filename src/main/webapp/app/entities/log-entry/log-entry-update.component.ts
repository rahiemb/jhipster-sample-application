import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILogEntry } from 'app/shared/model/log-entry.model';
import { LogEntryService } from './log-entry.service';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document';
import { IManual } from 'app/shared/model/manual.model';
import { ManualService } from 'app/entities/manual';
import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from 'app/entities/organization';
import { ISection } from 'app/shared/model/section.model';
import { SectionService } from 'app/entities/section';

@Component({
    selector: 'jhi-log-entry-update',
    templateUrl: './log-entry-update.component.html'
})
export class LogEntryUpdateComponent implements OnInit {
    logEntry: ILogEntry;
    isSaving: boolean;

    documents: IDocument[];

    manuals: IManual[];

    organizations: IOrganization[];

    sections: ISection[];
    timestampDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected logEntryService: LogEntryService,
        protected documentService: DocumentService,
        protected manualService: ManualService,
        protected organizationService: OrganizationService,
        protected sectionService: SectionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ logEntry }) => {
            this.logEntry = logEntry;
        });
        this.documentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDocument[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDocument[]>) => response.body)
            )
            .subscribe((res: IDocument[]) => (this.documents = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.manualService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IManual[]>) => mayBeOk.ok),
                map((response: HttpResponse<IManual[]>) => response.body)
            )
            .subscribe((res: IManual[]) => (this.manuals = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.organizationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOrganization[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOrganization[]>) => response.body)
            )
            .subscribe((res: IOrganization[]) => (this.organizations = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.logEntry.id !== undefined) {
            this.subscribeToSaveResponse(this.logEntryService.update(this.logEntry));
        } else {
            this.subscribeToSaveResponse(this.logEntryService.create(this.logEntry));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILogEntry>>) {
        result.subscribe((res: HttpResponse<ILogEntry>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackManualById(index: number, item: IManual) {
        return item.id;
    }

    trackOrganizationById(index: number, item: IOrganization) {
        return item.id;
    }

    trackSectionById(index: number, item: ISection) {
        return item.id;
    }
}
