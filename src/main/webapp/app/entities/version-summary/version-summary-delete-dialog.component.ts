import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVersionSummary } from 'app/shared/model/version-summary.model';
import { VersionSummaryService } from './version-summary.service';

@Component({
    selector: 'jhi-version-summary-delete-dialog',
    templateUrl: './version-summary-delete-dialog.component.html'
})
export class VersionSummaryDeleteDialogComponent {
    versionSummary: IVersionSummary;

    constructor(
        protected versionSummaryService: VersionSummaryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.versionSummaryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'versionSummaryListModification',
                content: 'Deleted an versionSummary'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-version-summary-delete-popup',
    template: ''
})
export class VersionSummaryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ versionSummary }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VersionSummaryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.versionSummary = versionSummary;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/version-summary', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/version-summary', { outlets: { popup: null } }]);
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
