import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PatternGrvComponent } from './pattern-grv.component';
import { PatternGrvDetailComponent } from './pattern-grv-detail.component';
import { PatternGrvPopupComponent } from './pattern-grv-dialog.component';
import { PatternGrvDeletePopupComponent } from './pattern-grv-delete-dialog.component';

@Injectable()
export class PatternGrvResolvePagingParams implements Resolve<any> {

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

export const patternRoute: Routes = [
    {
        path: 'pattern-grv',
        component: PatternGrvComponent,
        resolve: {
            'pagingParams': PatternGrvResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Patterns'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pattern-grv/:id',
        component: PatternGrvDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Patterns'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const patternPopupRoute: Routes = [
    {
        path: 'pattern-grv-new',
        component: PatternGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Patterns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pattern-grv/:id/edit',
        component: PatternGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Patterns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pattern-grv/:id/delete',
        component: PatternGrvDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Patterns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
