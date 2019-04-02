import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISection } from 'app/shared/model/section.model';
import { SectionService } from './section.service';
import { IManual } from 'app/shared/model/manual.model';
import { ManualService } from 'app/entities/manual';
import { ILock } from 'app/shared/model/lock.model';
import { LockService } from 'app/entities/lock';

@Component({
    selector: 'jhi-section-update',
    templateUrl: './section-update.component.html'
})
export class SectionUpdateComponent implements OnInit {
    section: ISection;
    isSaving: boolean;

    manuals: IManual[];

    locks: ILock[];
    retiredDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected sectionService: SectionService,
        protected manualService: ManualService,
        protected lockService: LockService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ section }) => {
            this.section = section;
        });
        this.manualService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IManual[]>) => mayBeOk.ok),
                map((response: HttpResponse<IManual[]>) => response.body)
            )
            .subscribe((res: IManual[]) => (this.manuals = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.lockService
            .query({ filter: 'section-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ILock[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILock[]>) => response.body)
            )
            .subscribe(
                (res: ILock[]) => {
                    if (!this.section.lock || !this.section.lock.id) {
                        this.locks = res;
                    } else {
                        this.lockService
                            .find(this.section.lock.id)
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
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.section.id !== undefined) {
            this.subscribeToSaveResponse(this.sectionService.update(this.section));
        } else {
            this.subscribeToSaveResponse(this.sectionService.create(this.section));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISection>>) {
        result.subscribe((res: HttpResponse<ISection>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLockById(index: number, item: ILock) {
        return item.id;
    }
}
