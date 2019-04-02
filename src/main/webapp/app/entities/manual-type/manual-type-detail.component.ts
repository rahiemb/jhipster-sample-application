import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IManualType } from 'app/shared/model/manual-type.model';

@Component({
    selector: 'jhi-manual-type-detail',
    templateUrl: './manual-type-detail.component.html'
})
export class ManualTypeDetailComponent implements OnInit {
    manualType: IManualType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ manualType }) => {
            this.manualType = manualType;
        });
    }

    previousState() {
        window.history.back();
    }
}
