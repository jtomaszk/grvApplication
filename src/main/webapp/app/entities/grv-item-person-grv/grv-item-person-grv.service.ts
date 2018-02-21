import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { GrvItemPersonGrv } from './grv-item-person-grv.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<GrvItemPersonGrv>;

@Injectable()
export class GrvItemPersonGrvService {

    private resourceUrl =  SERVER_API_URL + 'api/grv-item-people';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/grv-item-people';

    constructor(private http: HttpClient) { }

    create(grvItemPerson: GrvItemPersonGrv): Observable<EntityResponseType> {
        const copy = this.convert(grvItemPerson);
        return this.http.post<GrvItemPersonGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(grvItemPerson: GrvItemPersonGrv): Observable<EntityResponseType> {
        const copy = this.convert(grvItemPerson);
        return this.http.put<GrvItemPersonGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<GrvItemPersonGrv>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<GrvItemPersonGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<GrvItemPersonGrv[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<GrvItemPersonGrv[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<GrvItemPersonGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<GrvItemPersonGrv[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<GrvItemPersonGrv[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: GrvItemPersonGrv = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<GrvItemPersonGrv[]>): HttpResponse<GrvItemPersonGrv[]> {
        const jsonResponse: GrvItemPersonGrv[] = res.body;
        const body: GrvItemPersonGrv[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to GrvItemPersonGrv.
     */
    private convertItemFromServer(grvItemPerson: GrvItemPersonGrv): GrvItemPersonGrv {
        const copy: GrvItemPersonGrv = Object.assign({}, grvItemPerson);
        return copy;
    }

    /**
     * Convert a GrvItemPersonGrv to a JSON which can be sent to the server.
     */
    private convert(grvItemPerson: GrvItemPersonGrv): GrvItemPersonGrv {
        const copy: GrvItemPersonGrv = Object.assign({}, grvItemPerson);
        return copy;
    }
}
