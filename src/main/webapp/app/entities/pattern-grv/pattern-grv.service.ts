import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PatternGrv } from './pattern-grv.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PatternGrv>;

@Injectable()
export class PatternGrvService {

    private resourceUrl =  SERVER_API_URL + 'api/patterns';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/patterns';

    constructor(private http: HttpClient) { }

    create(pattern: PatternGrv): Observable<EntityResponseType> {
        const copy = this.convert(pattern);
        return this.http.post<PatternGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pattern: PatternGrv): Observable<EntityResponseType> {
        const copy = this.convert(pattern);
        return this.http.put<PatternGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PatternGrv>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PatternGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<PatternGrv[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PatternGrv[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<PatternGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<PatternGrv[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PatternGrv[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PatternGrv = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PatternGrv[]>): HttpResponse<PatternGrv[]> {
        const jsonResponse: PatternGrv[] = res.body;
        const body: PatternGrv[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PatternGrv.
     */
    private convertItemFromServer(pattern: PatternGrv): PatternGrv {
        const copy: PatternGrv = Object.assign({}, pattern);
        return copy;
    }

    /**
     * Convert a PatternGrv to a JSON which can be sent to the server.
     */
    private convert(pattern: PatternGrv): PatternGrv {
        const copy: PatternGrv = Object.assign({}, pattern);
        return copy;
    }
}
