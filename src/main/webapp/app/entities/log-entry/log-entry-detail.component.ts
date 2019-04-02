import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILogEntry } from 'app/shared/model/log-entry.model';

@Component({
    selector: 'jhi-log-entry-detail',
    templateUrl: './log-entry-detail.component.html'
})
export class LogEntryDetailComponent implements OnInit {
    logEntry: ILogEntry;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ logEntry }) => {
            this.logEntry = logEntry;
        });
    }

    previousState() {
        window.history.back();
    }
}
