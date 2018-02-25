import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AreaGrvComponent } from './area-grv.component';
import { AreaGrvDetailComponent } from './area-grv-detail.component';
import { AreaGrvPopupComponent } from './area-grv-dialog.component';
import { AreaGrvDeletePopupComponent } from './area-grv-delete-dialog.component';

export const areaRoute: Routes = [
    {
        path: 'area-grv',
        component: AreaGrvComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Areas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'area-grv/:id',
        component: AreaGrvDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Areas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const areaPopupRoute: Routes = [
    {
        path: 'area-grv-new',
        component: AreaGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Areas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'area-grv/:id/edit',
        component: AreaGrvPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Areas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'area-grv/:id/delete',
        component: AreaGrvDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Areas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
