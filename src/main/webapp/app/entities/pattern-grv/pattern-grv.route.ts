import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PatternGrvComponent } from './pattern-grv.component';
import { PatternGrvDetailComponent } from './pattern-grv-detail.component';
import { PatternGrvPopupComponent } from './pattern-grv-dialog.component';
import { PatternGrvDeletePopupComponent } from './pattern-grv-delete-dialog.component';

export const patternRoute: Routes = [
    {
        path: 'pattern-grv',
        component: PatternGrvComponent,
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
