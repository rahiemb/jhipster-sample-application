import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Ability } from 'app/shared/model/ability.model';
import { AbilityService } from './ability.service';
import { AbilityComponent } from './ability.component';
import { AbilityDetailComponent } from './ability-detail.component';
import { AbilityUpdateComponent } from './ability-update.component';
import { AbilityDeletePopupComponent } from './ability-delete-dialog.component';
import { IAbility } from 'app/shared/model/ability.model';

@Injectable({ providedIn: 'root' })
export class AbilityResolve implements Resolve<IAbility> {
    constructor(private service: AbilityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAbility> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Ability>) => response.ok),
                map((ability: HttpResponse<Ability>) => ability.body)
            );
        }
        return of(new Ability());
    }
}

export const abilityRoute: Routes = [
    {
        path: '',
        component: AbilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.ability.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AbilityDetailComponent,
        resolve: {
            ability: AbilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.ability.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AbilityUpdateComponent,
        resolve: {
            ability: AbilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.ability.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AbilityUpdateComponent,
        resolve: {
            ability: AbilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.ability.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const abilityPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AbilityDeletePopupComponent,
        resolve: {
            ability: AbilityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.ability.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
