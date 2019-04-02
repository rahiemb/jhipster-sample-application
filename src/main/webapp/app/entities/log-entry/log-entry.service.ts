import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILogEntry } from 'app/shared/model/log-entry.model';

type EntityResponseType = HttpResponse<ILogEntry>;
type EntityArrayResponseType = HttpResponse<ILogEntry[]>;

@Injectable({ providedIn: 'root' })
export class LogEntryService {
    public resourceUrl = SERVER_API_URL + 'api/log-entries';

    constructor(protected http: HttpClient) {}

    create(logEntry: ILogEntry): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(logEntry);
        return this.http
            .post<ILogEntry>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(logEntry: ILogEntry): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(logEntry);
        return this.http
            .put<ILogEntry>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILogEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILogEntry[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(logEntry: ILogEntry): ILogEntry {
        const copy: ILogEntry = Object.assign({}, logEntry, {
            timestamp: logEntry.timestamp != null && logEntry.timestamp.isValid() ? logEntry.timestamp.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.timestamp = res.body.timestamp != null ? moment(res.body.timestamp) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((logEntry: ILogEntry) => {
                logEntry.timestamp = logEntry.timestamp != null ? moment(logEntry.timestamp) : null;
            });
        }
        return res;
    }
}
