import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {ErrorGrv} from './error-grv.model';
import {createRequestOption} from '../../shared';

export type EntityResponseType = HttpResponse<ErrorGrv>;

@Injectable()
export class ErrorGrvService {

    private resourceUrl =  SERVER_API_URL + 'api/errors';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/errors';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(error: ErrorGrv): Observable<EntityResponseType> {
        const copy = this.convert(error);
        return this.http.post<ErrorGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(error: ErrorGrv): Observable<EntityResponseType> {
        const copy = this.convert(error);
        return this.http.put<ErrorGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ErrorGrv>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ErrorGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<ErrorGrv[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ErrorGrv[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<ErrorGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<ErrorGrv[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ErrorGrv[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ErrorGrv = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ErrorGrv[]>): HttpResponse<ErrorGrv[]> {
        const jsonResponse: ErrorGrv[] = res.body;
        const body: ErrorGrv[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ErrorGrv.
     */
    private convertItemFromServer(error: ErrorGrv): ErrorGrv {
        const copy: ErrorGrv = Object.assign({}, error);
        copy.createdDate = this.dateUtils
            .convertDateTimeFromServer(error.createdDate);
        return copy;
    }

    /**
     * Convert a ErrorGrv to a JSON which can be sent to the server.
     */
    private convert(error: ErrorGrv): ErrorGrv {
        const copy: ErrorGrv = Object.assign({}, error);

        copy.createdDate = this.dateUtils.toDate(error.createdDate);
        return copy;
    }
}
