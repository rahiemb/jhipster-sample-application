import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocumentTemplate } from 'app/shared/model/document-template.model';

type EntityResponseType = HttpResponse<IDocumentTemplate>;
type EntityArrayResponseType = HttpResponse<IDocumentTemplate[]>;

@Injectable({ providedIn: 'root' })
export class DocumentTemplateService {
    public resourceUrl = SERVER_API_URL + 'api/document-templates';

    constructor(protected http: HttpClient) {}

    create(documentTemplate: IDocumentTemplate): Observable<EntityResponseType> {
        return this.http.post<IDocumentTemplate>(this.resourceUrl, documentTemplate, { observe: 'response' });
    }

    update(documentTemplate: IDocumentTemplate): Observable<EntityResponseType> {
        return this.http.put<IDocumentTemplate>(this.resourceUrl, documentTemplate, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDocumentTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDocumentTemplate[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
