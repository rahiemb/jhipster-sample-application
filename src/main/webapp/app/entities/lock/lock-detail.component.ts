import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILock } from 'app/shared/model/lock.model';

@Component({
    selector: 'jhi-lock-detail',
    templateUrl: './lock-detail.component.html'
})
export class LockDetailComponent implements OnInit {
    lock: ILock;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lock }) => {
            this.lock = lock;
        });
    }

    previousState() {
        window.history.back();
    }
}
