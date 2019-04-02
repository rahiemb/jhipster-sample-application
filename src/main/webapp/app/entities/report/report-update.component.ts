import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IReport } from 'app/shared/model/report.model';
import { ReportService } from './report.service';
import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from 'app/entities/organization';

@Component({
    selector: 'jhi-report-update',
    templateUrl: './report-update.component.html'
})
export class ReportUpdateComponent implements OnInit {
    report: IReport;
    isSaving: boolean;

    organizations: IOrganization[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected reportService: ReportService,
        protected organizationService: OrganizationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ report }) => {
            this.report = report;
        });
        this.organizationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOrganization[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOrganization[]>) => response.body)
            )
            .subscribe((res: IOrganization[]) => (this.organizations = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.report.id !== undefined) {
            this.subscribeToSaveResponse(this.reportService.update(this.report));
        } else {
            this.subscribeToSaveResponse(this.reportService.create(this.report));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReport>>) {
        result.subscribe((res: HttpResponse<IReport>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOrganizationById(index: number, item: IOrganization) {
        return item.id;
    }
}
