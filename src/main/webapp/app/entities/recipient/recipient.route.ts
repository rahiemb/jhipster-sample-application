import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Recipient } from 'app/shared/model/recipient.model';
import { RecipientService } from './recipient.service';
import { RecipientComponent } from './recipient.component';
import { RecipientDetailComponent } from './recipient-detail.component';
import { RecipientUpdateComponent } from './recipient-update.component';
import { RecipientDeletePopupComponent } from './recipient-delete-dialog.component';
import { IRecipient } from 'app/shared/model/recipient.model';

@Injectable({ providedIn: 'root' })
export class RecipientResolve implements Resolve<IRecipient> {
    constructor(private service: RecipientService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRecipient> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Recipient>) => response.ok),
                map((recipient: HttpResponse<Recipient>) => recipient.body)
            );
        }
        return of(new Recipient());
    }
}

export const recipientRoute: Routes = [
    {
        path: '',
        component: RecipientComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.recipient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RecipientDetailComponent,
        resolve: {
            recipient: RecipientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.recipient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RecipientUpdateComponent,
        resolve: {
            recipient: RecipientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.recipient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RecipientUpdateComponent,
        resolve: {
            recipient: RecipientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.recipient.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recipientPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RecipientDeletePopupComponent,
        resolve: {
            recipient: RecipientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.recipient.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
