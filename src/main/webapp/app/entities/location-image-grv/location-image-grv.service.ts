import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {LocationImageGrv} from './location-image-grv.model';
import {createRequestOption} from '../../shared';

export type EntityResponseType = HttpResponse<LocationImageGrv>;

@Injectable()
export class LocationImageGrvService {

    private resourceUrl =  SERVER_API_URL + 'api/location-images';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/location-images';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(locationImage: LocationImageGrv): Observable<EntityResponseType> {
        const copy = this.convert(locationImage);
        return this.http.post<LocationImageGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(locationImage: LocationImageGrv): Observable<EntityResponseType> {
        const copy = this.convert(locationImage);
        return this.http.put<LocationImageGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LocationImageGrv>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LocationImageGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<LocationImageGrv[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LocationImageGrv[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<LocationImageGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<LocationImageGrv[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LocationImageGrv[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LocationImageGrv = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LocationImageGrv[]>): HttpResponse<LocationImageGrv[]> {
        const jsonResponse: LocationImageGrv[] = res.body;
        const body: LocationImageGrv[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LocationImageGrv.
     */
    private convertItemFromServer(locationImage: LocationImageGrv): LocationImageGrv {
        const copy: LocationImageGrv = Object.assign({}, locationImage);
        copy.createdDate = this.dateUtils
            .convertDateTimeFromServer(locationImage.createdDate);
        return copy;
    }

    /**
     * Convert a LocationImageGrv to a JSON which can be sent to the server.
     */
    private convert(locationImage: LocationImageGrv): LocationImageGrv {
        const copy: LocationImageGrv = Object.assign({}, locationImage);

        copy.createdDate = this.dateUtils.toDate(locationImage.createdDate);
        return copy;
    }
}
