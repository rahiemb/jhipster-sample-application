import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IManual } from 'app/shared/model/manual.model';

@Component({
    selector: 'jhi-manual-detail',
    templateUrl: './manual-detail.component.html'
})
export class ManualDetailComponent implements OnInit {
    manual: IManual;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ manual }) => {
            this.manual = manual;
        });
    }

    previousState() {
        window.history.back();
    }
}
