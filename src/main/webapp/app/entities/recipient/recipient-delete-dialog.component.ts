import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecipient } from 'app/shared/model/recipient.model';
import { RecipientService } from './recipient.service';

@Component({
    selector: 'jhi-recipient-delete-dialog',
    templateUrl: './recipient-delete-dialog.component.html'
})
export class RecipientDeleteDialogComponent {
    recipient: IRecipient;

    constructor(
        protected recipientService: RecipientService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recipientService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'recipientListModification',
                content: 'Deleted an recipient'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recipient-delete-popup',
    template: ''
})
export class RecipientDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recipient }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RecipientDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.recipient = recipient;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/recipient', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/recipient', { outlets: { popup: null } }]);
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
