import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IManual } from 'app/shared/model/manual.model';
import { ManualService } from './manual.service';

@Component({
    selector: 'jhi-manual-delete-dialog',
    templateUrl: './manual-delete-dialog.component.html'
})
export class ManualDeleteDialogComponent {
    manual: IManual;

    constructor(protected manualService: ManualService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.manualService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'manualListModification',
                content: 'Deleted an manual'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-manual-delete-popup',
    template: ''
})
export class ManualDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ manual }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ManualDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.manual = manual;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/manual', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/manual', { outlets: { popup: null } }]);
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
