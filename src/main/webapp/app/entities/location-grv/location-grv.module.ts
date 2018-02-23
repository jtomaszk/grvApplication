import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {GrvApplicationSharedModule} from '../../shared';
import {
    LocationGrvComponent,
    LocationGrvDeleteDialogComponent,
    LocationGrvDeletePopupComponent,
    LocationGrvDetailComponent,
    LocationGrvDialogComponent,
    LocationGrvPopupComponent,
    LocationGrvPopupService,
    LocationGrvService,
    locationPopupRoute,
    locationRoute,
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
