import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {SERVER_API_URL} from '../../app.constants';

import {JhiDateUtils} from 'ng-jhipster';

import {SourceArchiveGrv} from './source-archive-grv.model';
import {createRequestOption} from '../../shared';

export type EntityResponseType = HttpResponse<SourceArchiveGrv>;

@Injectable()
export class SourceArchiveGrvService {

    private resourceUrl =  SERVER_API_URL + 'api/source-archives';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/source-archives';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(sourceArchive: SourceArchiveGrv): Observable<EntityResponseType> {
        const copy = this.convert(sourceArchive);
        return this.http.post<SourceArchiveGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(sourceArchive: SourceArchiveGrv): Observable<EntityResponseType> {
        const copy = this.convert(sourceArchive);
        return this.http.put<SourceArchiveGrv>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SourceArchiveGrv>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SourceArchiveGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<SourceArchiveGrv[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SourceArchiveGrv[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<SourceArchiveGrv[]>> {
        const options = createRequestOption(req);
        return this.http.get<SourceArchiveGrv[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SourceArchiveGrv[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SourceArchiveGrv = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SourceArchiveGrv[]>): HttpResponse<SourceArchiveGrv[]> {
        const jsonResponse: SourceArchiveGrv[] = res.body;
        const body: SourceArchiveGrv[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SourceArchiveGrv.
     */
    private convertItemFromServer(sourceArchive: SourceArchiveGrv): SourceArchiveGrv {
        const copy: SourceArchiveGrv = Object.assign({}, sourceArchive);
        copy.createdDate = this.dateUtils
            .convertDateTimeFromServer(sourceArchive.createdDate);
        return copy;
    }

    /**
     * Convert a SourceArchiveGrv to a JSON which can be sent to the server.
     */
    private convert(sourceArchive: SourceArchiveGrv): SourceArchiveGrv {
        const copy: SourceArchiveGrv = Object.assign({}, sourceArchive);

        copy.createdDate = this.dateUtils.toDate(sourceArchive.createdDate);
        return copy;
    }
}
