import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IManualType } from 'app/shared/model/manual-type.model';
import { ManualTypeService } from './manual-type.service';

@Component({
    selector: 'jhi-manual-type-update',
    templateUrl: './manual-type-update.component.html'
})
export class ManualTypeUpdateComponent implements OnInit {
    manualType: IManualType;
    isSaving: boolean;

    constructor(protected manualTypeService: ManualTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ manualType }) => {
            this.manualType = manualType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.manualType.id !== undefined) {
            this.subscribeToSaveResponse(this.manualTypeService.update(this.manualType));
        } else {
            this.subscribeToSaveResponse(this.manualTypeService.create(this.manualType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IManualType>>) {
        result.subscribe((res: HttpResponse<IManualType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
