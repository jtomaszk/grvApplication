import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {LocationImageGrvComponent} from './location-image-grv.component';
import {LocationImageGrvDetailComponent} from './location-image-grv-detail.component';
import {LocationImageGrvPopupComponent} from './location-image-grv-dialog.component';
import {LocationImageGrvDeletePopupComponent} from './location-image-grv-delete-dialog.component';

export const locationImageRoute: Routes = [
    {
        path: 'location-image-grv',
        component: LocationImageGrvComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LocationImages'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'location-image-grv/:id',
        component: LocationImageGrvDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LocationImages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const locationImagePopupRoute: Routes = [
    {
        path: 'location-image-grv-new',
        component: LocationImageGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LocationImages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'location-image-grv/:id/edit',
        component: LocationImageGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LocationImages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'location-image-grv/:id/delete',
        component: LocationImageGrvDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LocationImages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
