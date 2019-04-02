import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DocumentTemplate } from 'app/shared/model/document-template.model';
import { DocumentTemplateService } from './document-template.service';
import { DocumentTemplateComponent } from './document-template.component';
import { DocumentTemplateDetailComponent } from './document-template-detail.component';
import { DocumentTemplateUpdateComponent } from './document-template-update.component';
import { DocumentTemplateDeletePopupComponent } from './document-template-delete-dialog.component';
import { IDocumentTemplate } from 'app/shared/model/document-template.model';

@Injectable({ providedIn: 'root' })
export class DocumentTemplateResolve implements Resolve<IDocumentTemplate> {
    constructor(private service: DocumentTemplateService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDocumentTemplate> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DocumentTemplate>) => response.ok),
                map((documentTemplate: HttpResponse<DocumentTemplate>) => documentTemplate.body)
            );
        }
        return of(new DocumentTemplate());
    }
}

export const documentTemplateRoute: Routes = [
    {
        path: '',
        component: DocumentTemplateComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.documentTemplate.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DocumentTemplateDetailComponent,
        resolve: {
            documentTemplate: DocumentTemplateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.documentTemplate.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DocumentTemplateUpdateComponent,
        resolve: {
            documentTemplate: DocumentTemplateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.documentTemplate.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DocumentTemplateUpdateComponent,
        resolve: {
            documentTemplate: DocumentTemplateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.documentTemplate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentTemplatePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DocumentTemplateDeletePopupComponent,
        resolve: {
            documentTemplate: DocumentTemplateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.documentTemplate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
