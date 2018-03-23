import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {GrvApplicationSharedModule} from '../../shared';
import {
    SourceArchiveGrvComponent,
    SourceArchiveGrvDeleteDialogComponent,
    SourceArchiveGrvDeletePopupComponent,
    SourceArchiveGrvDetailComponent,
    SourceArchiveGrvDialogComponent,
    SourceArchiveGrvPopupComponent,
    SourceArchiveGrvPopupService,
    SourceArchiveGrvResolvePagingParams,
    SourceArchiveGrvService,
    sourceArchivePopupRoute,
    sourceArchiveRoute,
} from './';

const ENTITY_STATES = [
    ...sourceArchiveRoute,
    ...sourceArchivePopupRoute,
];

@NgModule({
    imports: [
        GrvApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SourceArchiveGrvComponent,
        SourceArchiveGrvDetailComponent,
        SourceArchiveGrvDialogComponent,
        SourceArchiveGrvDeleteDialogComponent,
        SourceArchiveGrvPopupComponent,
        SourceArchiveGrvDeletePopupComponent,
    ],
    entryComponents: [
        SourceArchiveGrvComponent,
        SourceArchiveGrvDialogComponent,
        SourceArchiveGrvPopupComponent,
        SourceArchiveGrvDeleteDialogComponent,
        SourceArchiveGrvDeletePopupComponent,
    ],
    providers: [
        SourceArchiveGrvService,
        SourceArchiveGrvPopupService,
        SourceArchiveGrvResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationSourceArchiveGrvModule {}
