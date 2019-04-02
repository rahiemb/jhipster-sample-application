import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVersionSummary } from 'app/shared/model/version-summary.model';

type EntityResponseType = HttpResponse<IVersionSummary>;
type EntityArrayResponseType = HttpResponse<IVersionSummary[]>;

@Injectable({ providedIn: 'root' })
export class VersionSummaryService {
    public resourceUrl = SERVER_API_URL + 'api/version-summaries';

    constructor(protected http: HttpClient) {}

    create(versionSummary: IVersionSummary): Observable<EntityResponseType> {
        return this.http.post<IVersionSummary>(this.resourceUrl, versionSummary, { observe: 'response' });
    }

    update(versionSummary: IVersionSummary): Observable<EntityResponseType> {
        return this.http.put<IVersionSummary>(this.resourceUrl, versionSummary, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVersionSummary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVersionSummary[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
