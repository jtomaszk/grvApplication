import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';
import {ParseErrorGrvComponent} from './parse-error-grv.component';
import {ParseErrorGrvDetailComponent} from './parse-error-grv-detail.component';
import {ParseErrorGrvPopupComponent} from './parse-error-grv-dialog.component';
import {ParseErrorGrvDeletePopupComponent} from './parse-error-grv-delete-dialog.component';

export const parseErrorRoute: Routes = [
    {
        path: 'parse-error-grv',
        component: ParseErrorGrvComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParseErrors'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'parse-error-grv/:id',
        component: ParseErrorGrvDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParseErrors'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parseErrorPopupRoute: Routes = [
    {
        path: 'parse-error-grv-new',
        component: ParseErrorGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParseErrors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parse-error-grv/:id/edit',
        component: ParseErrorGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParseErrors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parse-error-grv/:id/delete',
        component: ParseErrorGrvDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ParseErrors'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
