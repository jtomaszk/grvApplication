import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {SourceArchiveGrvComponent} from './source-archive-grv.component';
import {SourceArchiveGrvDetailComponent} from './source-archive-grv-detail.component';
import {SourceArchiveGrvPopupComponent} from './source-archive-grv-dialog.component';
import {SourceArchiveGrvDeletePopupComponent} from './source-archive-grv-delete-dialog.component';

@Injectable()
export class SourceArchiveGrvResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const sourceArchiveRoute: Routes = [
    {
        path: 'source-archive-grv',
        component: SourceArchiveGrvComponent,
        resolve: {
            'pagingParams': SourceArchiveGrvResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceArchives'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'source-archive-grv/:id',
        component: SourceArchiveGrvDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceArchives'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sourceArchivePopupRoute: Routes = [
    {
        path: 'source-archive-grv-new',
        component: SourceArchiveGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceArchives'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'source-archive-grv/:id/edit',
        component: SourceArchiveGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceArchives'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'source-archive-grv/:id/delete',
        component: SourceArchiveGrvDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SourceArchives'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
