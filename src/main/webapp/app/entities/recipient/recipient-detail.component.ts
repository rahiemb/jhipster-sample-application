import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecipient } from 'app/shared/model/recipient.model';

@Component({
    selector: 'jhi-recipient-detail',
    templateUrl: './recipient-detail.component.html'
})
export class RecipientDetailComponent implements OnInit {
    recipient: IRecipient;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recipient }) => {
            this.recipient = recipient;
        });
    }

    previousState() {
        window.history.back();
    }
}
