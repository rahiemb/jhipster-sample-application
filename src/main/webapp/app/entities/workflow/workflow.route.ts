import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Workflow } from 'app/shared/model/workflow.model';
import { WorkflowService } from './workflow.service';
import { WorkflowComponent } from './workflow.component';
import { WorkflowDetailComponent } from './workflow-detail.component';
import { WorkflowUpdateComponent } from './workflow-update.component';
import { WorkflowDeletePopupComponent } from './workflow-delete-dialog.component';
import { IWorkflow } from 'app/shared/model/workflow.model';

@Injectable({ providedIn: 'root' })
export class WorkflowResolve implements Resolve<IWorkflow> {
    constructor(private service: WorkflowService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IWorkflow> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Workflow>) => response.ok),
                map((workflow: HttpResponse<Workflow>) => workflow.body)
            );
        }
        return of(new Workflow());
    }
}

export const workflowRoute: Routes = [
    {
        path: '',
        component: WorkflowComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.workflow.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: WorkflowDetailComponent,
        resolve: {
            workflow: WorkflowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.workflow.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: WorkflowUpdateComponent,
        resolve: {
            workflow: WorkflowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.workflow.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: WorkflowUpdateComponent,
        resolve: {
            workflow: WorkflowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.workflow.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const workflowPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: WorkflowDeletePopupComponent,
        resolve: {
            workflow: WorkflowResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.workflow.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
