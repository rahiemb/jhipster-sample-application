import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LogEntry } from 'app/shared/model/log-entry.model';
import { LogEntryService } from './log-entry.service';
import { LogEntryComponent } from './log-entry.component';
import { LogEntryDetailComponent } from './log-entry-detail.component';
import { LogEntryUpdateComponent } from './log-entry-update.component';
import { LogEntryDeletePopupComponent } from './log-entry-delete-dialog.component';
import { ILogEntry } from 'app/shared/model/log-entry.model';

@Injectable({ providedIn: 'root' })
export class LogEntryResolve implements Resolve<ILogEntry> {
    constructor(private service: LogEntryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILogEntry> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LogEntry>) => response.ok),
                map((logEntry: HttpResponse<LogEntry>) => logEntry.body)
            );
        }
        return of(new LogEntry());
    }
}

export const logEntryRoute: Routes = [
    {
        path: '',
        component: LogEntryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.logEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: LogEntryDetailComponent,
        resolve: {
            logEntry: LogEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.logEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: LogEntryUpdateComponent,
        resolve: {
            logEntry: LogEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.logEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: LogEntryUpdateComponent,
        resolve: {
            logEntry: LogEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.logEntry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const logEntryPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: LogEntryDeletePopupComponent,
        resolve: {
            logEntry: LogEntryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationApp.logEntry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
