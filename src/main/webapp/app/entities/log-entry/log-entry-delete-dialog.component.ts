import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILogEntry } from 'app/shared/model/log-entry.model';
import { LogEntryService } from './log-entry.service';

@Component({
    selector: 'jhi-log-entry-delete-dialog',
    templateUrl: './log-entry-delete-dialog.component.html'
})
export class LogEntryDeleteDialogComponent {
    logEntry: ILogEntry;

    constructor(protected logEntryService: LogEntryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.logEntryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'logEntryListModification',
                content: 'Deleted an logEntry'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-log-entry-delete-popup',
    template: ''
})
export class LogEntryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ logEntry }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LogEntryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.logEntry = logEntry;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/log-entry', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/log-entry', { outlets: { popup: null } }]);
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
