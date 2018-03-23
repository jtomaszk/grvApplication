import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {GrvItemPersonGrvComponent} from './grv-item-person-grv.component';
import {GrvItemPersonGrvDetailComponent} from './grv-item-person-grv-detail.component';
import {GrvItemPersonGrvPopupComponent} from './grv-item-person-grv-dialog.component';
import {GrvItemPersonGrvDeletePopupComponent} from './grv-item-person-grv-delete-dialog.component';

export const grvItemPersonRoute: Routes = [
    {
        path: 'grv-item-person-grv',
        component: GrvItemPersonGrvComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrvItemPeople'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'grv-item-person-grv/:id',
        component: GrvItemPersonGrvDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrvItemPeople'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const grvItemPersonPopupRoute: Routes = [
    {
        path: 'grv-item-person-grv-new',
        component: GrvItemPersonGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrvItemPeople'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'grv-item-person-grv/:id/edit',
        component: GrvItemPersonGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrvItemPeople'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'grv-item-person-grv/:id/delete',
        component: GrvItemPersonGrvDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GrvItemPeople'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
