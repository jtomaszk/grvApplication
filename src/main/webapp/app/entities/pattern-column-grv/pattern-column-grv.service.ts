import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';

import {PatternColumnGrv} from './pattern-column-grv.model';
import {createRequestOption} from '../../shared';

export type EntityResponseType = HttpResponse<PatternColumnGrv>;

@Injectable()
export class PatternColumnGrvService {

    private resourceUrl =  SERVER_API_URL + 'api/pattern-columns';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/pattern-columns';

    constructor(private http: HttpClient) { }

    create(patternColumn: PatternColumnGrv): Observable<EntityResponseType> {
        const copy = this.convert(patternColumn);
        return this.http.post<PatternColumnGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(patternColumn: PatternColumnGrv): Observable<EntityResponseType> {
        const copy = this.convert(patternColumn);
        return this.http.put<PatternColumnGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PatternColumnGrv>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PatternColumnGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<PatternColumnGrv[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PatternColumnGrv[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<PatternColumnGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<PatternColumnGrv[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PatternColumnGrv[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PatternColumnGrv = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PatternColumnGrv[]>): HttpResponse<PatternColumnGrv[]> {
        const jsonResponse: PatternColumnGrv[] = res.body;
        const body: PatternColumnGrv[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PatternColumnGrv.
     */
    private convertItemFromServer(patternColumn: PatternColumnGrv): PatternColumnGrv {
        const copy: PatternColumnGrv = Object.assign({}, patternColumn);
        return copy;
    }

    /**
     * Convert a PatternColumnGrv to a JSON which can be sent to the server.
     */
    private convert(patternColumn: PatternColumnGrv): PatternColumnGrv {
        const copy: PatternColumnGrv = Object.assign({}, patternColumn);
        return copy;
    }
}
