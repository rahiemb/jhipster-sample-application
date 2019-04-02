import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IManual } from 'app/shared/model/manual.model';

type EntityResponseType = HttpResponse<IManual>;
type EntityArrayResponseType = HttpResponse<IManual[]>;

@Injectable({ providedIn: 'root' })
export class ManualService {
    public resourceUrl = SERVER_API_URL + 'api/manuals';

    constructor(protected http: HttpClient) {}

    create(manual: IManual): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(manual);
        return this.http
            .post<IManual>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(manual: IManual): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(manual);
        return this.http
            .put<IManual>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IManual>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IManual[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(manual: IManual): IManual {
        const copy: IManual = Object.assign({}, manual, {
            retiredDate: manual.retiredDate != null && manual.retiredDate.isValid() ? manual.retiredDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.retiredDate = res.body.retiredDate != null ? moment(res.body.retiredDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((manual: IManual) => {
                manual.retiredDate = manual.retiredDate != null ? moment(manual.retiredDate) : null;
            });
        }
        return res;
    }
}
