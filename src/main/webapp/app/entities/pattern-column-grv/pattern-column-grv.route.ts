import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {PatternColumnGrvComponent} from './pattern-column-grv.component';
import {PatternColumnGrvDetailComponent} from './pattern-column-grv-detail.component';
import {PatternColumnGrvPopupComponent} from './pattern-column-grv-dialog.component';
import {PatternColumnGrvDeletePopupComponent} from './pattern-column-grv-delete-dialog.component';

@Injectable()
export class PatternColumnGrvResolvePagingParams implements Resolve<any> {

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

export const patternColumnRoute: Routes = [
    {
        path: 'pattern-column-grv',
        component: PatternColumnGrvComponent,
        resolve: {
            'pagingParams': PatternColumnGrvResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PatternColumns'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pattern-column-grv/:id',
        component: PatternColumnGrvDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PatternColumns'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const patternColumnPopupRoute: Routes = [
    {
        path: 'pattern-column-grv-new',
        component: PatternColumnGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PatternColumns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pattern-column-grv/:id/edit',
        component: PatternColumnGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PatternColumns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pattern-column-grv/:id/delete',
        component: PatternColumnGrvDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PatternColumns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
