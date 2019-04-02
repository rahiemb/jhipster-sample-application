import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPart } from 'app/shared/model/part.model';
import { PartService } from './part.service';
import { IVersion } from 'app/shared/model/version.model';
import { VersionService } from 'app/entities/version';

@Component({
    selector: 'jhi-part-update',
    templateUrl: './part-update.component.html'
})
export class PartUpdateComponent implements OnInit {
    part: IPart;
    isSaving: boolean;

    children: IPart[];

    versions: IVersion[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected partService: PartService,
        protected versionService: VersionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ part }) => {
            this.part = part;
        });
        this.partService
            .query({ filter: 'part-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IPart[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPart[]>) => response.body)
            )
            .subscribe(
                (res: IPart[]) => {
                    if (!this.part.children || !this.part.children.id) {
                        this.children = res;
                    } else {
                        this.partService
                            .find(this.part.children.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IPart>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IPart>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IPart) => (this.children = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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
        if (this.part.id !== undefined) {
            this.subscribeToSaveResponse(this.partService.update(this.part));
        } else {
            this.subscribeToSaveResponse(this.partService.create(this.part));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPart>>) {
        result.subscribe((res: HttpResponse<IPart>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPartById(index: number, item: IPart) {
        return item.id;
    }

    trackVersionById(index: number, item: IVersion) {
        return item.id;
    }
}
