import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrvApplicationSharedModule } from '../../shared';
import {
    LocationImageGrvService,
    LocationImageGrvPopupService,
    LocationImageGrvComponent,
    LocationImageGrvDetailComponent,
    LocationImageGrvDialogComponent,
    LocationImageGrvPopupComponent,
    LocationImageGrvDeletePopupComponent,
    LocationImageGrvDeleteDialogComponent,
    locationImageRoute,
    locationImagePopupRoute,
} from './';

const ENTITY_STATES = [
    ...locationImageRoute,
    ...locationImagePopupRoute,
];

@NgModule({
    imports: [
        GrvApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LocationImageGrvComponent,
        LocationImageGrvDetailComponent,
        LocationImageGrvDialogComponent,
        LocationImageGrvDeleteDialogComponent,
        LocationImageGrvPopupComponent,
        LocationImageGrvDeletePopupComponent,
    ],
    entryComponents: [
        LocationImageGrvComponent,
        LocationImageGrvDialogComponent,
        LocationImageGrvPopupComponent,
        LocationImageGrvDeleteDialogComponent,
        LocationImageGrvDeletePopupComponent,
    ],
    providers: [
        LocationImageGrvService,
        LocationImageGrvPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationLocationImageGrvModule {}
