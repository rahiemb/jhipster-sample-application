import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Lock } from 'app/shared/model/lock.model';
import { LockService } from './lock.service';
import { LockComponent } from './lock.component';
import { LockDetailComponent } from './lock-detail.component';
import { LockUpdateComponent } from './lock-update.component';
import { LockDeletePopupComponent } from './lock-delete-dialog.component';
import { ILock } from 'app/shared/model/lock.model';

@Injectable({ providedIn: 'root' })
export class LockResolve implements Resolve<ILock> {
    constructor(private service: LockService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILock> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Lock>) => response.ok),
                map((lock: HttpResponse<Lock>) => lock.body)
            );
        }
        return of(new Lock());
    }
}

export const lockRoute: Routes = [
    {
        path: '',
        component: LockComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.lock.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LockDetailComponent,
        resolve: {
            lock: LockResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.lock.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LockUpdateComponent,
        resolve: {
            lock: LockResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.lock.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LockUpdateComponent,
        resolve: {
            lock: LockResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.lock.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lockPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LockDeletePopupComponent,
        resolve: {
            lock: LockResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.lock.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
