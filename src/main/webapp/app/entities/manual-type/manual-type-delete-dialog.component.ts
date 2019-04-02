import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IManualType } from 'app/shared/model/manual-type.model';
import { ManualTypeService } from './manual-type.service';

@Component({
    selector: 'jhi-manual-type-delete-dialog',
    templateUrl: './manual-type-delete-dialog.component.html'
})
export class ManualTypeDeleteDialogComponent {
    manualType: IManualType;

    constructor(
        protected manualTypeService: ManualTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.manualTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'manualTypeListModification',
                content: 'Deleted an manualType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-manual-type-delete-popup',
    template: ''
})
export class ManualTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ manualType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ManualTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.manualType = manualType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/manual-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/manual-type', { outlets: { popup: null } }]);
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
