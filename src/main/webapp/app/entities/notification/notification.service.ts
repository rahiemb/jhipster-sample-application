import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INotification } from 'app/shared/model/notification.model';

type EntityResponseType = HttpResponse<INotification>;
type EntityArrayResponseType = HttpResponse<INotification[]>;

@Injectable({ providedIn: 'root' })
export class NotificationService {
    public resourceUrl = SERVER_API_URL + 'api/notifications';

    constructor(protected http: HttpClient) {}

    create(notification: INotification): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(notification);
        return this.http
            .post<INotification>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(notification: INotification): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(notification);
        return this.http
            .put<INotification>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<INotification>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<INotification[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(notification: INotification): INotification {
        const copy: INotification = Object.assign({}, notification, {
            beforeDate:
                notification.beforeDate != null && notification.beforeDate.isValid() ? notification.beforeDate.format(DATE_FORMAT) : null,
            afterDate:
                notification.afterDate != null && notification.afterDate.isValid() ? notification.afterDate.format(DATE_FORMAT) : null,
            sendDate: notification.sendDate != null && notification.sendDate.isValid() ? notification.sendDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.beforeDate = res.body.beforeDate != null ? moment(res.body.beforeDate) : null;
            res.body.afterDate = res.body.afterDate != null ? moment(res.body.afterDate) : null;
            res.body.sendDate = res.body.sendDate != null ? moment(res.body.sendDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((notification: INotification) => {
                notification.beforeDate = notification.beforeDate != null ? moment(notification.beforeDate) : null;
                notification.afterDate = notification.afterDate != null ? moment(notification.afterDate) : null;
                notification.sendDate = notification.sendDate != null ? moment(notification.sendDate) : null;
            });
        }
        return res;
    }
}
