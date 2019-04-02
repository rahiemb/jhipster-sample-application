import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVersionSummary } from 'app/shared/model/version-summary.model';

@Component({
    selector: 'jhi-version-summary-detail',
    templateUrl: './version-summary-detail.component.html'
})
export class VersionSummaryDetailComponent implements OnInit {
    versionSummary: IVersionSummary;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ versionSummary }) => {
            this.versionSummary = versionSummary;
        });
    }

    previousState() {
        window.history.back();
    }
}
