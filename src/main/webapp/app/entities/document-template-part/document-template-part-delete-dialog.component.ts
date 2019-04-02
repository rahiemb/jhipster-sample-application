import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentTemplatePart } from 'app/shared/model/document-template-part.model';
import { DocumentTemplatePartService } from './document-template-part.service';

@Component({
    selector: 'jhi-document-template-part-delete-dialog',
    templateUrl: './document-template-part-delete-dialog.component.html'
})
export class DocumentTemplatePartDeleteDialogComponent {
    documentTemplatePart: IDocumentTemplatePart;

    constructor(
        protected documentTemplatePartService: DocumentTemplatePartService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentTemplatePartService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'documentTemplatePartListModification',
                content: 'Deleted an documentTemplatePart'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-document-template-part-delete-popup',
    template: ''
})
export class DocumentTemplatePartDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentTemplatePart }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DocumentTemplatePartDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.documentTemplatePart = documentTemplatePart;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/document-template-part', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/document-template-part', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
