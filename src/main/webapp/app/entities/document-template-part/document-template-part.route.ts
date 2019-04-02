import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DocumentTemplatePart } from 'app/shared/model/document-template-part.model';
import { DocumentTemplatePartService } from './document-template-part.service';
import { DocumentTemplatePartComponent } from './document-template-part.component';
import { DocumentTemplatePartDetailComponent } from './document-template-part-detail.component';
import { DocumentTemplatePartUpdateComponent } from './document-template-part-update.component';
import { DocumentTemplatePartDeletePopupComponent } from './document-template-part-delete-dialog.component';
import { IDocumentTemplatePart } from 'app/shared/model/document-template-part.model';

@Injectable({ providedIn: 'root' })
export class DocumentTemplatePartResolve implements Resolve<IDocumentTemplatePart> {
    constructor(private service: DocumentTemplatePartService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDocumentTemplatePart> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DocumentTemplatePart>) => response.ok),
                map((documentTemplatePart: HttpResponse<DocumentTemplatePart>) => documentTemplatePart.body)
            );
        }
        return of(new DocumentTemplatePart());
    }
}

export const documentTemplatePartRoute: Routes = [
    {
        path: '',
        component: DocumentTemplatePartComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.documentTemplatePart.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DocumentTemplatePartDetailComponent,
        resolve: {
            documentTemplatePart: DocumentTemplatePartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.documentTemplatePart.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DocumentTemplatePartUpdateComponent,
        resolve: {
            documentTemplatePart: DocumentTemplatePartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.documentTemplatePart.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DocumentTemplatePartUpdateComponent,
        resolve: {
            documentTemplatePart: DocumentTemplatePartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.documentTemplatePart.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentTemplatePartPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DocumentTemplatePartDeletePopupComponent,
        resolve: {
            documentTemplatePart: DocumentTemplatePartResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.documentTemplatePart.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
