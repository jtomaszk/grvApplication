import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrvApplicationSharedModule } from '../../shared';
import {
    LocationGrvService,
    LocationGrvPopupService,
    LocationGrvComponent,
    LocationGrvDetailComponent,
    LocationGrvDialogComponent,
    LocationGrvPopupComponent,
    LocationGrvDeletePopupComponent,
    LocationGrvDeleteDialogComponent,
    locationRoute,
    locationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...locationRoute,
    ...locationPopupRoute,
];

@NgModule({
    imports: [
        GrvApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LocationGrvComponent,
        LocationGrvDetailComponent,
        LocationGrvDialogComponent,
        LocationGrvDeleteDialogComponent,
        LocationGrvPopupComponent,
        LocationGrvDeletePopupComponent,
    ],
    entryComponents: [
        LocationGrvComponent,
        LocationGrvDialogComponent,
        LocationGrvPopupComponent,
        LocationGrvDeleteDialogComponent,
        LocationGrvDeletePopupComponent,
    ],
    providers: [
        LocationGrvService,
        LocationGrvPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationLocationGrvModule {}
