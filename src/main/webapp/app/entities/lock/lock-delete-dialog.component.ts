import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILock } from 'app/shared/model/lock.model';
import { LockService } from './lock.service';

@Component({
    selector: 'jhi-lock-delete-dialog',
    templateUrl: './lock-delete-dialog.component.html'
})
export class LockDeleteDialogComponent {
    lock: ILock;

    constructor(protected lockService: LockService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lockService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'lockListModification',
                content: 'Deleted an lock'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lock-delete-popup',
    template: ''
})
export class LockDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lock }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LockDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.lock = lock;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/lock', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/lock', { outlets: { popup: null } }]);
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
