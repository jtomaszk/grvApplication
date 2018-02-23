import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';

import {AreaGrv} from './area-grv.model';
import {createRequestOption} from '../../shared';

export type EntityResponseType = HttpResponse<AreaGrv>;

@Injectable()
export class AreaGrvService {

    private resourceUrl =  SERVER_API_URL + 'api/areas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/areas';

    constructor(private http: HttpClient) { }

    create(area: AreaGrv): Observable<EntityResponseType> {
        const copy = this.convert(area);
        return this.http.post<AreaGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(area: AreaGrv): Observable<EntityResponseType> {
        const copy = this.convert(area);
        return this.http.put<AreaGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AreaGrv>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AreaGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<AreaGrv[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AreaGrv[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<AreaGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<AreaGrv[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AreaGrv[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AreaGrv = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AreaGrv[]>): HttpResponse<AreaGrv[]> {
        const jsonResponse: AreaGrv[] = res.body;
        const body: AreaGrv[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AreaGrv.
     */
    private convertItemFromServer(area: AreaGrv): AreaGrv {
        const copy: AreaGrv = Object.assign({}, area);
        return copy;
    }

    /**
     * Convert a AreaGrv to a JSON which can be sent to the server.
     */
    private convert(area: AreaGrv): AreaGrv {
        const copy: AreaGrv = Object.assign({}, area);
        return copy;
    }
}
