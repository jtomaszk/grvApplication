import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {GrvApplicationSharedModule} from '../../shared';
import {
    GrvItemGrvComponent,
    GrvItemGrvDeleteDialogComponent,
    GrvItemGrvDeletePopupComponent,
    GrvItemGrvDetailComponent,
    GrvItemGrvDialogComponent,
    GrvItemGrvPopupComponent,
    GrvItemGrvPopupService,
    GrvItemGrvService,
    grvItemPopupRoute,
    grvItemRoute,
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
