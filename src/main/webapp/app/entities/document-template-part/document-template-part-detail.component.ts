import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDocumentTemplatePart } from 'app/shared/model/document-template-part.model';

@Component({
    selector: 'jhi-document-template-part-detail',
    templateUrl: './document-template-part-detail.component.html'
})
export class DocumentTemplatePartDetailComponent implements OnInit {
    documentTemplatePart: IDocumentTemplatePart;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentTemplatePart }) => {
            this.documentTemplatePart = documentTemplatePart;
        });
    }

    previousState() {
        window.history.back();
    }
}
