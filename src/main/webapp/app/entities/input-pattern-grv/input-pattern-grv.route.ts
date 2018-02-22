import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { InputPatternGrvComponent } from './input-pattern-grv.component';
import { InputPatternGrvDetailComponent } from './input-pattern-grv-detail.component';
import { InputPatternGrvPopupComponent } from './input-pattern-grv-dialog.component';
import { InputPatternGrvDeletePopupComponent } from './input-pattern-grv-delete-dialog.component';

@Injectable()
export class InputPatternGrvResolvePagingParams implements Resolve<any> {

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

export const inputPatternRoute: Routes = [
    {
        path: 'input-pattern-grv',
        component: InputPatternGrvComponent,
        resolve: {
            'pagingParams': InputPatternGrvResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InputPatterns'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'input-pattern-grv/:id',
        component: InputPatternGrvDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InputPatterns'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inputPatternPopupRoute: Routes = [
    {
        path: 'input-pattern-grv-new',
        component: InputPatternGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InputPatterns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'input-pattern-grv/:id/edit',
        component: InputPatternGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InputPatterns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'input-pattern-grv/:id/delete',
        component: InputPatternGrvDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'InputPatterns'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
