import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecipient } from 'app/shared/model/recipient.model';

type EntityResponseType = HttpResponse<IRecipient>;
type EntityArrayResponseType = HttpResponse<IRecipient[]>;

@Injectable({ providedIn: 'root' })
export class RecipientService {
    public resourceUrl = SERVER_API_URL + 'api/recipients';

    constructor(protected http: HttpClient) {}

    create(recipient: IRecipient): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(recipient);
        return this.http
            .post<IRecipient>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(recipient: IRecipient): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(recipient);
        return this.http
            .put<IRecipient>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRecipient>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRecipient[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(recipient: IRecipient): IRecipient {
        const copy: IRecipient = Object.assign({}, recipient, {
            timestamp: recipient.timestamp != null && recipient.timestamp.isValid() ? recipient.timestamp.format(DATE_FORMAT) : null
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
            res.body.forEach((recipient: IRecipient) => {
                recipient.timestamp = recipient.timestamp != null ? moment(recipient.timestamp) : null;
            });
        }
        return res;
    }
}
