import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {GrvApplicationSharedModule} from '../../shared';
import {
    AreaGrvComponent,
    AreaGrvDeleteDialogComponent,
    AreaGrvDeletePopupComponent,
    AreaGrvDetailComponent,
    AreaGrvDialogComponent,
    AreaGrvPopupComponent,
    AreaGrvPopupService,
    AreaGrvService,
    areaPopupRoute,
    areaRoute,
} from './';

const ENTITY_STATES = [
    ...areaRoute,
    ...areaPopupRoute,
];

@NgModule({
    imports: [
        GrvApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AreaGrvComponent,
        AreaGrvDetailComponent,
        AreaGrvDialogComponent,
        AreaGrvDeleteDialogComponent,
        AreaGrvPopupComponent,
        AreaGrvDeletePopupComponent,
    ],
    entryComponents: [
        AreaGrvComponent,
        AreaGrvDialogComponent,
        AreaGrvPopupComponent,
        AreaGrvDeleteDialogComponent,
        AreaGrvDeletePopupComponent,
    ],
    providers: [
        AreaGrvService,
        AreaGrvPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationAreaGrvModule {}
