import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocument } from 'app/shared/model/document.model';

type EntityResponseType = HttpResponse<IDocument>;
type EntityArrayResponseType = HttpResponse<IDocument[]>;

@Injectable({ providedIn: 'root' })
export class DocumentService {
    public resourceUrl = SERVER_API_URL + 'api/documents';

    constructor(protected http: HttpClient) {}

    create(document: IDocument): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(document);
        return this.http
            .post<IDocument>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(document: IDocument): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(document);
        return this.http
            .put<IDocument>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDocument[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(document: IDocument): IDocument {
        const copy: IDocument = Object.assign({}, document, {
            effectiveDate:
                document.effectiveDate != null && document.effectiveDate.isValid() ? document.effectiveDate.format(DATE_FORMAT) : null,
            approvalDate:
                document.approvalDate != null && document.approvalDate.isValid() ? document.approvalDate.format(DATE_FORMAT) : null,
            supersedesDate:
                document.supersedesDate != null && document.supersedesDate.isValid() ? document.supersedesDate.format(DATE_FORMAT) : null,
            originalDate:
                document.originalDate != null && document.originalDate.isValid() ? document.originalDate.format(DATE_FORMAT) : null,
            reviewDate: document.reviewDate != null && document.reviewDate.isValid() ? document.reviewDate.format(DATE_FORMAT) : null,
            revisionDate:
                document.revisionDate != null && document.revisionDate.isValid() ? document.revisionDate.format(DATE_FORMAT) : null,
            expirationDate:
                document.expirationDate != null && document.expirationDate.isValid() ? document.expirationDate.format(DATE_FORMAT) : null,
            retiredDate: document.retiredDate != null && document.retiredDate.isValid() ? document.retiredDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.effectiveDate = res.body.effectiveDate != null ? moment(res.body.effectiveDate) : null;
            res.body.approvalDate = res.body.approvalDate != null ? moment(res.body.approvalDate) : null;
            res.body.supersedesDate = res.body.supersedesDate != null ? moment(res.body.supersedesDate) : null;
            res.body.originalDate = res.body.originalDate != null ? moment(res.body.originalDate) : null;
            res.body.reviewDate = res.body.reviewDate != null ? moment(res.body.reviewDate) : null;
            res.body.revisionDate = res.body.revisionDate != null ? moment(res.body.revisionDate) : null;
            res.body.expirationDate = res.body.expirationDate != null ? moment(res.body.expirationDate) : null;
            res.body.retiredDate = res.body.retiredDate != null ? moment(res.body.retiredDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((document: IDocument) => {
                document.effectiveDate = document.effectiveDate != null ? moment(document.effectiveDate) : null;
                document.approvalDate = document.approvalDate != null ? moment(document.approvalDate) : null;
                document.supersedesDate = document.supersedesDate != null ? moment(document.supersedesDate) : null;
                document.originalDate = document.originalDate != null ? moment(document.originalDate) : null;
                document.reviewDate = document.reviewDate != null ? moment(document.reviewDate) : null;
                document.revisionDate = document.revisionDate != null ? moment(document.revisionDate) : null;
                document.expirationDate = document.expirationDate != null ? moment(document.expirationDate) : null;
                document.retiredDate = document.retiredDate != null ? moment(document.retiredDate) : null;
            });
        }
        return res;
    }
}
