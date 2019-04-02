import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentTemplate } from 'app/shared/model/document-template.model';

@Component({
    selector: 'jhi-document-template-detail',
    templateUrl: './document-template-detail.component.html'
})
export class DocumentTemplateDetailComponent implements OnInit {
    documentTemplate: IDocumentTemplate;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentTemplate }) => {
            this.documentTemplate = documentTemplate;
        });
    }

    previousState() {
        window.history.back();
    }
}
