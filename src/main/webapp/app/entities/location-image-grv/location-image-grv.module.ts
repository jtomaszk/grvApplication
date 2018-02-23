import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {GrvApplicationSharedModule} from '../../shared';
import {
    LocationImageGrvComponent,
    LocationImageGrvDeleteDialogComponent,
    LocationImageGrvDeletePopupComponent,
    LocationImageGrvDetailComponent,
    LocationImageGrvDialogComponent,
    LocationImageGrvPopupComponent,
    LocationImageGrvPopupService,
    LocationImageGrvService,
    locationImagePopupRoute,
    locationImageRoute,
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
