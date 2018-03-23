import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {LocationGrvComponent} from './location-grv.component';
import {LocationGrvDetailComponent} from './location-grv-detail.component';
import {LocationGrvPopupComponent} from './location-grv-dialog.component';
import {LocationGrvDeletePopupComponent} from './location-grv-delete-dialog.component';

export const locationRoute: Routes = [
    {
        path: 'location-grv',
        component: LocationGrvComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'location-grv/:id',
        component: LocationGrvDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const locationPopupRoute: Routes = [
    {
        path: 'location-grv-new',
        component: LocationGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'location-grv/:id/edit',
        component: LocationGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'location-grv/:id/delete',
        component: LocationGrvDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Locations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
