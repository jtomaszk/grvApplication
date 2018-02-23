import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';

import {InputPatternGrv} from './input-pattern-grv.model';
import {createRequestOption} from '../../shared';

export type EntityResponseType = HttpResponse<InputPatternGrv>;

@Injectable()
export class InputPatternGrvService {

    private resourceUrl =  SERVER_API_URL + 'api/input-patterns';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/input-patterns';

    constructor(private http: HttpClient) { }

    create(inputPattern: InputPatternGrv): Observable<EntityResponseType> {
        const copy = this.convert(inputPattern);
        return this.http.post<InputPatternGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(inputPattern: InputPatternGrv): Observable<EntityResponseType> {
        const copy = this.convert(inputPattern);
        return this.http.put<InputPatternGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<InputPatternGrv>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<InputPatternGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<InputPatternGrv[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<InputPatternGrv[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<InputPatternGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<InputPatternGrv[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<InputPatternGrv[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: InputPatternGrv = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<InputPatternGrv[]>): HttpResponse<InputPatternGrv[]> {
        const jsonResponse: InputPatternGrv[] = res.body;
        const body: InputPatternGrv[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to InputPatternGrv.
     */
    private convertItemFromServer(inputPattern: InputPatternGrv): InputPatternGrv {
        const copy: InputPatternGrv = Object.assign({}, inputPattern);
        return copy;
    }

    /**
     * Convert a InputPatternGrv to a JSON which can be sent to the server.
     */
    private convert(inputPattern: InputPatternGrv): InputPatternGrv {
        const copy: InputPatternGrv = Object.assign({}, inputPattern);
        return copy;
    }
}
