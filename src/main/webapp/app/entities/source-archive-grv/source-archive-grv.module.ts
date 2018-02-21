import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrvApplicationSharedModule } from '../../shared';
import {
    SourceArchiveGrvService,
    SourceArchiveGrvPopupService,
    SourceArchiveGrvComponent,
    SourceArchiveGrvDetailComponent,
    SourceArchiveGrvDialogComponent,
    SourceArchiveGrvPopupComponent,
    SourceArchiveGrvDeletePopupComponent,
    SourceArchiveGrvDeleteDialogComponent,
    sourceArchiveRoute,
    sourceArchivePopupRoute,
    SourceArchiveGrvResolvePagingParams,
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
