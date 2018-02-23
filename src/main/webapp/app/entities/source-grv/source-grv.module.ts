import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {GrvApplicationSharedModule} from '../../shared';
import {
    SourceGrvComponent,
    SourceGrvDeleteDialogComponent,
    SourceGrvDeletePopupComponent,
    SourceGrvDetailComponent,
    SourceGrvDialogComponent,
    SourceGrvPopupComponent,
    SourceGrvPopupService,
    SourceGrvService,
    sourcePopupRoute,
    sourceRoute,
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
