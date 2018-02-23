import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {LocationGrv} from './location-grv.model';
import {createRequestOption} from '../../shared';

export type EntityResponseType = HttpResponse<LocationGrv>;

@Injectable()
export class LocationGrvService {

    private resourceUrl =  SERVER_API_URL + 'api/locations';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/locations';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(location: LocationGrv): Observable<EntityResponseType> {
        const copy = this.convert(location);
        return this.http.post<LocationGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(location: LocationGrv): Observable<EntityResponseType> {
        const copy = this.convert(location);
        return this.http.put<LocationGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LocationGrv>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LocationGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<LocationGrv[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LocationGrv[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<LocationGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<LocationGrv[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LocationGrv[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LocationGrv = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LocationGrv[]>): HttpResponse<LocationGrv[]> {
        const jsonResponse: LocationGrv[] = res.body;
        const body: LocationGrv[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LocationGrv.
     */
    private convertItemFromServer(location: LocationGrv): LocationGrv {
        const copy: LocationGrv = Object.assign({}, location);
        copy.createdDate = this.dateUtils
            .convertDateTimeFromServer(location.createdDate);
        return copy;
    }

    /**
     * Convert a LocationGrv to a JSON which can be sent to the server.
     */
    private convert(location: LocationGrv): LocationGrv {
        const copy: LocationGrv = Object.assign({}, location);

        copy.createdDate = this.dateUtils.toDate(location.createdDate);
        return copy;
    }
}
