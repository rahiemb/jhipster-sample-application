import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Title } from 'app/shared/model/title.model';
import { TitleService } from './title.service';
import { TitleComponent } from './title.component';
import { TitleDetailComponent } from './title-detail.component';
import { TitleUpdateComponent } from './title-update.component';
import { TitleDeletePopupComponent } from './title-delete-dialog.component';
import { ITitle } from 'app/shared/model/title.model';

@Injectable({ providedIn: 'root' })
export class TitleResolve implements Resolve<ITitle> {
    constructor(private service: TitleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITitle> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Title>) => response.ok),
                map((title: HttpResponse<Title>) => title.body)
            );
        }
        return of(new Title());
    }
}

export const titleRoute: Routes = [
    {
        path: '',
        component: TitleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.title.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TitleDetailComponent,
        resolve: {
            title: TitleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.title.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TitleUpdateComponent,
        resolve: {
            title: TitleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.title.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TitleUpdateComponent,
        resolve: {
            title: TitleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.title.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const titlePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TitleDeletePopupComponent,
        resolve: {
            title: TitleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.title.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
