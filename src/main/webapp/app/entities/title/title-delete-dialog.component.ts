import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITitle } from 'app/shared/model/title.model';
import { TitleService } from './title.service';

@Component({
    selector: 'jhi-title-delete-dialog',
    templateUrl: './title-delete-dialog.component.html'
})
export class TitleDeleteDialogComponent {
    title: ITitle;

    constructor(protected titleService: TitleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.titleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'titleListModification',
                content: 'Deleted an title'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-title-delete-popup',
    template: ''
})
export class TitleDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ title }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TitleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.title = title;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/title', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/title', { outlets: { popup: null } }]);
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
