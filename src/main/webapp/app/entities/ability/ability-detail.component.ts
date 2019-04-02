import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAbility } from 'app/shared/model/ability.model';

@Component({
    selector: 'jhi-ability-detail',
    templateUrl: './ability-detail.component.html'
})
export class AbilityDetailComponent implements OnInit {
    ability: IAbility;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ability }) => {
            this.ability = ability;
        });
    }

    previousState() {
        window.history.back();
    }
}
