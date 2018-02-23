import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {SourceGrv} from './source-grv.model';
import {createRequestOption} from '../../shared';

export type EntityResponseType = HttpResponse<SourceGrv>;

@Injectable()
export class SourceGrvService {

    private resourceUrl =  SERVER_API_URL + 'api/sources';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/sources';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(source: SourceGrv): Observable<EntityResponseType> {
        const copy = this.convert(source);
        return this.http.post<SourceGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(source: SourceGrv): Observable<EntityResponseType> {
        const copy = this.convert(source);
        return this.http.put<SourceGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SourceGrv>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SourceGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<SourceGrv[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SourceGrv[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<SourceGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<SourceGrv[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SourceGrv[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SourceGrv = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SourceGrv[]>): HttpResponse<SourceGrv[]> {
        const jsonResponse: SourceGrv[] = res.body;
        const body: SourceGrv[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SourceGrv.
     */
    private convertItemFromServer(source: SourceGrv): SourceGrv {
        const copy: SourceGrv = Object.assign({}, source);
        copy.lastRunDate = this.dateUtils
            .convertDateTimeFromServer(source.lastRunDate);
        return copy;
    }

    /**
     * Convert a SourceGrv to a JSON which can be sent to the server.
     */
    private convert(source: SourceGrv): SourceGrv {
        const copy: SourceGrv = Object.assign({}, source);

        copy.lastRunDate = this.dateUtils.toDate(source.lastRunDate);
        return copy;
    }

    runLoad(source: SourceGrv) {
        return this.http.put(`${this.resourceUrl}/${source.id}/run`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }
}
