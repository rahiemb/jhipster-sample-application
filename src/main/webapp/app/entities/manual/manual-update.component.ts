import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IManual } from 'app/shared/model/manual.model';
import { ManualService } from './manual.service';
import { ILock } from 'app/shared/model/lock.model';
import { LockService } from 'app/entities/lock';
import { IDocumentTemplate } from 'app/shared/model/document-template.model';
import { DocumentTemplateService } from 'app/entities/document-template';
import { IManualType } from 'app/shared/model/manual-type.model';
import { ManualTypeService } from 'app/entities/manual-type';
import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from 'app/entities/organization';

@Component({
    selector: 'jhi-manual-update',
    templateUrl: './manual-update.component.html'
})
export class ManualUpdateComponent implements OnInit {
    manual: IManual;
    isSaving: boolean;

    locks: ILock[];

    defaulttemplates: IDocumentTemplate[];

    manualtypes: IManualType[];

    organizations: IOrganization[];
    retiredDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected manualService: ManualService,
        protected lockService: LockService,
        protected documentTemplateService: DocumentTemplateService,
        protected manualTypeService: ManualTypeService,
        protected organizationService: OrganizationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ manual }) => {
            this.manual = manual;
        });
        this.lockService
            .query({ filter: 'manual-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ILock[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILock[]>) => response.body)
            )
            .subscribe(
                (res: ILock[]) => {
                    if (!this.manual.lock || !this.manual.lock.id) {
                        this.locks = res;
                    } else {
                        this.lockService
                            .find(this.manual.lock.id)
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
        this.documentTemplateService
            .query({ filter: 'manual-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IDocumentTemplate[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDocumentTemplate[]>) => response.body)
            )
            .subscribe(
                (res: IDocumentTemplate[]) => {
                    if (!this.manual.defaultTemplate || !this.manual.defaultTemplate.id) {
                        this.defaulttemplates = res;
                    } else {
                        this.documentTemplateService
                            .find(this.manual.defaultTemplate.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IDocumentTemplate>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IDocumentTemplate>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IDocumentTemplate) => (this.defaulttemplates = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.manualTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IManualType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IManualType[]>) => response.body)
            )
            .subscribe((res: IManualType[]) => (this.manualtypes = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.manual.id !== undefined) {
            this.subscribeToSaveResponse(this.manualService.update(this.manual));
        } else {
            this.subscribeToSaveResponse(this.manualService.create(this.manual));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IManual>>) {
        result.subscribe((res: HttpResponse<IManual>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDocumentTemplateById(index: number, item: IDocumentTemplate) {
        return item.id;
    }

    trackManualTypeById(index: number, item: IManualType) {
        return item.id;
    }

    trackOrganizationById(index: number, item: IOrganization) {
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
