import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IManualType } from 'app/shared/model/manual-type.model';

type EntityResponseType = HttpResponse<IManualType>;
type EntityArrayResponseType = HttpResponse<IManualType[]>;

@Injectable({ providedIn: 'root' })
export class ManualTypeService {
    public resourceUrl = SERVER_API_URL + 'api/manual-types';

    constructor(protected http: HttpClient) {}

    create(manualType: IManualType): Observable<EntityResponseType> {
        return this.http.post<IManualType>(this.resourceUrl, manualType, { observe: 'response' });
    }

    update(manualType: IManualType): Observable<EntityResponseType> {
        return this.http.put<IManualType>(this.resourceUrl, manualType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IManualType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IManualType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
