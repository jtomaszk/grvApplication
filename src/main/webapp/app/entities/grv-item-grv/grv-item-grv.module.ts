import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrvApplicationSharedModule } from '../../shared';
import {
    GrvItemGrvService,
    GrvItemGrvPopupService,
    GrvItemGrvComponent,
    GrvItemGrvDetailComponent,
    GrvItemGrvDialogComponent,
    GrvItemGrvPopupComponent,
    GrvItemGrvDeletePopupComponent,
    GrvItemGrvDeleteDialogComponent,
    grvItemRoute,
    grvItemPopupRoute,
} from './';

const ENTITY_STATES = [
    ...grvItemRoute,
    ...grvItemPopupRoute,
];

@NgModule({
    imports: [
        GrvApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GrvItemGrvComponent,
        GrvItemGrvDetailComponent,
        GrvItemGrvDialogComponent,
        GrvItemGrvDeleteDialogComponent,
        GrvItemGrvPopupComponent,
        GrvItemGrvDeletePopupComponent,
    ],
    entryComponents: [
        GrvItemGrvComponent,
        GrvItemGrvDialogComponent,
        GrvItemGrvPopupComponent,
        GrvItemGrvDeleteDialogComponent,
        GrvItemGrvDeletePopupComponent,
    ],
    providers: [
        GrvItemGrvService,
        GrvItemGrvPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationGrvItemGrvModule {}
