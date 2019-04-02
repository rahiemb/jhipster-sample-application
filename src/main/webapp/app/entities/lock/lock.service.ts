import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILock } from 'app/shared/model/lock.model';

type EntityResponseType = HttpResponse<ILock>;
type EntityArrayResponseType = HttpResponse<ILock[]>;

@Injectable({ providedIn: 'root' })
export class LockService {
    public resourceUrl = SERVER_API_URL + 'api/locks';

    constructor(protected http: HttpClient) {}

    create(lock: ILock): Observable<EntityResponseType> {
        return this.http.post<ILock>(this.resourceUrl, lock, { observe: 'response' });
    }

    update(lock: ILock): Observable<EntityResponseType> {
        return this.http.put<ILock>(this.resourceUrl, lock, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILock>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILock[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
