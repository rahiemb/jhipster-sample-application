import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocumentTemplatePart } from 'app/shared/model/document-template-part.model';

type EntityResponseType = HttpResponse<IDocumentTemplatePart>;
type EntityArrayResponseType = HttpResponse<IDocumentTemplatePart[]>;

@Injectable({ providedIn: 'root' })
export class DocumentTemplatePartService {
    public resourceUrl = SERVER_API_URL + 'api/document-template-parts';

    constructor(protected http: HttpClient) {}

    create(documentTemplatePart: IDocumentTemplatePart): Observable<EntityResponseType> {
        return this.http.post<IDocumentTemplatePart>(this.resourceUrl, documentTemplatePart, { observe: 'response' });
    }

    update(documentTemplatePart: IDocumentTemplatePart): Observable<EntityResponseType> {
        return this.http.put<IDocumentTemplatePart>(this.resourceUrl, documentTemplatePart, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDocumentTemplatePart>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDocumentTemplatePart[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
