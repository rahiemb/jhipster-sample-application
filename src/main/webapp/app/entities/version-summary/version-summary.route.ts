import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VersionSummary } from 'app/shared/model/version-summary.model';
import { VersionSummaryService } from './version-summary.service';
import { VersionSummaryComponent } from './version-summary.component';
import { VersionSummaryDetailComponent } from './version-summary-detail.component';
import { VersionSummaryUpdateComponent } from './version-summary-update.component';
import { VersionSummaryDeletePopupComponent } from './version-summary-delete-dialog.component';
import { IVersionSummary } from 'app/shared/model/version-summary.model';

@Injectable({ providedIn: 'root' })
export class VersionSummaryResolve implements Resolve<IVersionSummary> {
    constructor(private service: VersionSummaryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVersionSummary> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<VersionSummary>) => response.ok),
                map((versionSummary: HttpResponse<VersionSummary>) => versionSummary.body)
            );
        }
        return of(new VersionSummary());
    }
}

export const versionSummaryRoute: Routes = [
    {
        path: '',
        component: VersionSummaryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.versionSummary.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VersionSummaryDetailComponent,
        resolve: {
            versionSummary: VersionSummaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.versionSummary.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VersionSummaryUpdateComponent,
        resolve: {
            versionSummary: VersionSummaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.versionSummary.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VersionSummaryUpdateComponent,
        resolve: {
            versionSummary: VersionSummaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.versionSummary.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const versionSummaryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VersionSummaryDeletePopupComponent,
        resolve: {
            versionSummary: VersionSummaryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.versionSummary.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
