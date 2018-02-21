import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ErrorGrvComponent } from './error-grv.component';
import { ErrorGrvDetailComponent } from './error-grv-detail.component';
import { ErrorGrvPopupComponent } from './error-grv-dialog.component';
import { ErrorGrvDeletePopupComponent } from './error-grv-delete-dialog.component';

export const errorRoute: Routes = [
    {
        path: 'error-grv',
        component: ErrorGrvComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Errors'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'error-grv/:id',
        component: ErrorGrvDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Errors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const errorPopupRoute: Routes = [
    {
        path: 'error-grv-new',
        component: ErrorGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Errors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'error-grv/:id/edit',
        component: ErrorGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Errors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'error-grv/:id/delete',
        component: ErrorGrvDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Errors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
