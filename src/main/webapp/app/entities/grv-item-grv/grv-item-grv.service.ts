import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {GrvItemGrv} from './grv-item-grv.model';
import {createRequestOption} from '../../shared';

export type EntityResponseType = HttpResponse<GrvItemGrv>;

@Injectable()
export class GrvItemGrvService {

    private resourceUrl =  SERVER_API_URL + 'api/grv-items';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/grv-items';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(grvItem: GrvItemGrv): Observable<EntityResponseType> {
        const copy = this.convert(grvItem);
        return this.http.post<GrvItemGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(grvItem: GrvItemGrv): Observable<EntityResponseType> {
        const copy = this.convert(grvItem);
        return this.http.put<GrvItemGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<GrvItemGrv>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<GrvItemGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<GrvItemGrv[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<GrvItemGrv[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<GrvItemGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<GrvItemGrv[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<GrvItemGrv[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: GrvItemGrv = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<GrvItemGrv[]>): HttpResponse<GrvItemGrv[]> {
        const jsonResponse: GrvItemGrv[] = res.body;
        const body: GrvItemGrv[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to GrvItemGrv.
     */
    private convertItemFromServer(grvItem: GrvItemGrv): GrvItemGrv {
        const copy: GrvItemGrv = Object.assign({}, grvItem);
        copy.startDate = this.dateUtils
            .convertDateTimeFromServer(grvItem.startDate);
        copy.endDate = this.dateUtils
            .convertDateTimeFromServer(grvItem.endDate);
        copy.validToDate = this.dateUtils
            .convertDateTimeFromServer(grvItem.validToDate);
        copy.createdDate = this.dateUtils
            .convertDateTimeFromServer(grvItem.createdDate);
        return copy;
    }

    /**
     * Convert a GrvItemGrv to a JSON which can be sent to the server.
     */
    private convert(grvItem: GrvItemGrv): GrvItemGrv {
        const copy: GrvItemGrv = Object.assign({}, grvItem);

        copy.startDate = this.dateUtils.toDate(grvItem.startDate);

        copy.endDate = this.dateUtils.toDate(grvItem.endDate);

        copy.validToDate = this.dateUtils.toDate(grvItem.validToDate);

        copy.createdDate = this.dateUtils.toDate(grvItem.createdDate);
        return copy;
    }
}
