import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAbility } from 'app/shared/model/ability.model';
import { AbilityService } from './ability.service';

@Component({
    selector: 'jhi-ability-delete-dialog',
    templateUrl: './ability-delete-dialog.component.html'
})
export class AbilityDeleteDialogComponent {
    ability: IAbility;

    constructor(protected abilityService: AbilityService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.abilityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'abilityListModification',
                content: 'Deleted an ability'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ability-delete-popup',
    template: ''
})
export class AbilityDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ability }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AbilityDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.ability = ability;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/ability', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/ability', { outlets: { popup: null } }]);
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
