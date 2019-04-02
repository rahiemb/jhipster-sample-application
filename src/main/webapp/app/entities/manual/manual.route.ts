import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Manual } from 'app/shared/model/manual.model';
import { ManualService } from './manual.service';
import { ManualComponent } from './manual.component';
import { ManualDetailComponent } from './manual-detail.component';
import { ManualUpdateComponent } from './manual-update.component';
import { ManualDeletePopupComponent } from './manual-delete-dialog.component';
import { IManual } from 'app/shared/model/manual.model';

@Injectable({ providedIn: 'root' })
export class ManualResolve implements Resolve<IManual> {
    constructor(private service: ManualService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IManual> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Manual>) => response.ok),
                map((manual: HttpResponse<Manual>) => manual.body)
            );
        }
        return of(new Manual());
    }
}

export const manualRoute: Routes = [
    {
        path: '',
        component: ManualComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.manual.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ManualDetailComponent,
        resolve: {
            manual: ManualResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.manual.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ManualUpdateComponent,
        resolve: {
            manual: ManualResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.manual.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ManualUpdateComponent,
        resolve: {
            manual: ManualResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.manual.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const manualPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ManualDeletePopupComponent,
        resolve: {
            manual: ManualResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.manual.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
