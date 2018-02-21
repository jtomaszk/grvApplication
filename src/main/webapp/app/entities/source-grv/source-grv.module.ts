import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrvApplicationSharedModule } from '../../shared';
import {
    SourceGrvService,
    SourceGrvPopupService,
    SourceGrvComponent,
    SourceGrvDetailComponent,
    SourceGrvDialogComponent,
    SourceGrvPopupComponent,
    SourceGrvDeletePopupComponent,
    SourceGrvDeleteDialogComponent,
    sourceRoute,
    sourcePopupRoute,
} from './';

const ENTITY_STATES = [
    ...sourceRoute,
    ...sourcePopupRoute,
];

@NgModule({
    imports: [
        GrvApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SourceGrvComponent,
        SourceGrvDetailComponent,
        SourceGrvDialogComponent,
        SourceGrvDeleteDialogComponent,
        SourceGrvPopupComponent,
        SourceGrvDeletePopupComponent,
    ],
    entryComponents: [
        SourceGrvComponent,
        SourceGrvDialogComponent,
        SourceGrvPopupComponent,
        SourceGrvDeleteDialogComponent,
        SourceGrvDeletePopupComponent,
    ],
    providers: [
        SourceGrvService,
        SourceGrvPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationSourceGrvModule {}
