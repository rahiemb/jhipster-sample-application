import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITitle } from 'app/shared/model/title.model';

@Component({
    selector: 'jhi-title-detail',
    templateUrl: './title-detail.component.html'
})
export class TitleDetailComponent implements OnInit {
    title: ITitle;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ title }) => {
            this.title = title;
        });
    }

    previousState() {
        window.history.back();
    }
}
