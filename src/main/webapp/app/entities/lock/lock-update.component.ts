import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILock } from 'app/shared/model/lock.model';
import { LockService } from './lock.service';

@Component({
    selector: 'jhi-lock-update',
    templateUrl: './lock-update.component.html'
})
export class LockUpdateComponent implements OnInit {
    lock: ILock;
    isSaving: boolean;

    constructor(protected lockService: LockService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lock }) => {
            this.lock = lock;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lock.id !== undefined) {
            this.subscribeToSaveResponse(this.lockService.update(this.lock));
        } else {
            this.subscribeToSaveResponse(this.lockService.create(this.lock));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILock>>) {
        result.subscribe((res: HttpResponse<ILock>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
