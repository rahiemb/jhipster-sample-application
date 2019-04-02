import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ManualType } from 'app/shared/model/manual-type.model';
import { ManualTypeService } from './manual-type.service';
import { ManualTypeComponent } from './manual-type.component';
import { ManualTypeDetailComponent } from './manual-type-detail.component';
import { ManualTypeUpdateComponent } from './manual-type-update.component';
import { ManualTypeDeletePopupComponent } from './manual-type-delete-dialog.component';
import { IManualType } from 'app/shared/model/manual-type.model';

@Injectable({ providedIn: 'root' })
export class ManualTypeResolve implements Resolve<IManualType> {
    constructor(private service: ManualTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IManualType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ManualType>) => response.ok),
                map((manualType: HttpResponse<ManualType>) => manualType.body)
            );
        }
        return of(new ManualType());
    }
}

export const manualTypeRoute: Routes = [
    {
        path: '',
        component: ManualTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.manualType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ManualTypeDetailComponent,
        resolve: {
            manualType: ManualTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.manualType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ManualTypeUpdateComponent,
        resolve: {
            manualType: ManualTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.manualType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ManualTypeUpdateComponent,
        resolve: {
            manualType: ManualTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.manualType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const manualTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ManualTypeDeletePopupComponent,
        resolve: {
            manualType: ManualTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.manualType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
