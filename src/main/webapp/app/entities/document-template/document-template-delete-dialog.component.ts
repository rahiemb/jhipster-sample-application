import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentTemplate } from 'app/shared/model/document-template.model';
import { DocumentTemplateService } from './document-template.service';

@Component({
    selector: 'jhi-document-template-delete-dialog',
    templateUrl: './document-template-delete-dialog.component.html'
})
export class DocumentTemplateDeleteDialogComponent {
    documentTemplate: IDocumentTemplate;

    constructor(
        protected documentTemplateService: DocumentTemplateService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentTemplateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'documentTemplateListModification',
                content: 'Deleted an documentTemplate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-document-template-delete-popup',
    template: ''
})
export class DocumentTemplateDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentTemplate }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DocumentTemplateDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.documentTemplate = documentTemplate;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/document-template', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/document-template', { outlets: { popup: null } }]);
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
