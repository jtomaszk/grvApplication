import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SourceGrvComponent } from './source-grv.component';
import { SourceGrvDetailComponent } from './source-grv-detail.component';
import { SourceGrvPopupComponent } from './source-grv-dialog.component';
import { SourceGrvDeletePopupComponent } from './source-grv-delete-dialog.component';

export const sourceRoute: Routes = [
    {
        path: 'source-grv',
        component: SourceGrvComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sources'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'source-grv/:id',
        component: SourceGrvDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sources'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sourcePopupRoute: Routes = [
    {
        path: 'source-grv-new',
        component: SourceGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sources'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'source-grv/:id/edit',
        component: SourceGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sources'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'source-grv/:id/delete',
        component: SourceGrvDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Sources'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
