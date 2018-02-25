import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ParseErrorGrv } from './parse-error-grv.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ParseErrorGrv>;

@Injectable()
export class ParseErrorGrvService {

    private resourceUrl =  SERVER_API_URL + 'api/parse-errors';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/parse-errors';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(parseError: ParseErrorGrv): Observable<EntityResponseType> {
        const copy = this.convert(parseError);
        return this.http.post<ParseErrorGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(parseError: ParseErrorGrv): Observable<EntityResponseType> {
        const copy = this.convert(parseError);
        return this.http.put<ParseErrorGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ParseErrorGrv>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ParseErrorGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<ParseErrorGrv[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ParseErrorGrv[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<ParseErrorGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<ParseErrorGrv[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ParseErrorGrv[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ParseErrorGrv = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ParseErrorGrv[]>): HttpResponse<ParseErrorGrv[]> {
        const jsonResponse: ParseErrorGrv[] = res.body;
        const body: ParseErrorGrv[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ParseErrorGrv.
     */
    private convertItemFromServer(parseError: ParseErrorGrv): ParseErrorGrv {
        const copy: ParseErrorGrv = Object.assign({}, parseError);
        copy.createdDate = this.dateUtils
            .convertDateTimeFromServer(parseError.createdDate);
        return copy;
    }

    /**
     * Convert a ParseErrorGrv to a JSON which can be sent to the server.
     */
    private convert(parseError: ParseErrorGrv): ParseErrorGrv {
        const copy: ParseErrorGrv = Object.assign({}, parseError);

        copy.createdDate = this.dateUtils.toDate(parseError.createdDate);
        return copy;
    }
}
