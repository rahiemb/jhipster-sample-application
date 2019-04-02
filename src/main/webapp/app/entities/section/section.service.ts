import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISection } from 'app/shared/model/section.model';

type EntityResponseType = HttpResponse<ISection>;
type EntityArrayResponseType = HttpResponse<ISection[]>;

@Injectable({ providedIn: 'root' })
export class SectionService {
    public resourceUrl = SERVER_API_URL + 'api/sections';

    constructor(protected http: HttpClient) {}

    create(section: ISection): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(section);
        return this.http
            .post<ISection>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(section: ISection): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(section);
        return this.http
            .put<ISection>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISection>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISection[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(section: ISection): ISection {
        const copy: ISection = Object.assign({}, section, {
            retiredDate: section.retiredDate != null && section.retiredDate.isValid() ? section.retiredDate.format(DATE_FORMAT) : null
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
            res.body.forEach((section: ISection) => {
                section.retiredDate = section.retiredDate != null ? moment(section.retiredDate) : null;
            });
        }
        return res;
    }
}
