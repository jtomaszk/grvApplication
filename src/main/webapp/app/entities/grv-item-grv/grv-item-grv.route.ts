import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {GrvItemGrvComponent} from './grv-item-grv.component';
import {GrvItemGrvDetailComponent} from './grv-item-grv-detail.component';
import {GrvItemGrvPopupComponent} from './grv-item-grv-dialog.component';
import {GrvItemGrvDeletePopupComponent} from './grv-item-grv-delete-dialog.component';

export const grvItemRoute: Routes = [
    {
        path: 'grv-item-grv',
        component: GrvItemGrvComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrvItems'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'grv-item-grv/:id',
        component: GrvItemGrvDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrvItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const grvItemPopupRoute: Routes = [
    {
        path: 'grv-item-grv-new',
        component: GrvItemGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrvItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'grv-item-grv/:id/edit',
        component: GrvItemGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrvItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'grv-item-grv/:id/delete',
        component: GrvItemGrvDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrvItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
