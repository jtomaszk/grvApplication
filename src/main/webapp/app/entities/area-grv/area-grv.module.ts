import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrvApplicationSharedModule } from '../../shared';
import {
    AreaGrvService,
    AreaGrvPopupService,
    AreaGrvComponent,
    AreaGrvDetailComponent,
    AreaGrvDialogComponent,
    AreaGrvPopupComponent,
    AreaGrvDeletePopupComponent,
    AreaGrvDeleteDialogComponent,
    areaRoute,
    areaPopupRoute,
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
