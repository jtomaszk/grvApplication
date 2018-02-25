import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrvApplicationSharedModule } from '../../shared';
import {
    PatternColumnGrvService,
    PatternColumnGrvPopupService,
    PatternColumnGrvComponent,
    PatternColumnGrvDetailComponent,
    PatternColumnGrvDialogComponent,
    PatternColumnGrvPopupComponent,
    PatternColumnGrvDeletePopupComponent,
    PatternColumnGrvDeleteDialogComponent,
    patternColumnRoute,
    patternColumnPopupRoute,
    PatternColumnGrvResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...patternColumnRoute,
    ...patternColumnPopupRoute,
];

@NgModule({
    imports: [
        GrvApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PatternColumnGrvComponent,
        PatternColumnGrvDetailComponent,
        PatternColumnGrvDialogComponent,
        PatternColumnGrvDeleteDialogComponent,
        PatternColumnGrvPopupComponent,
        PatternColumnGrvDeletePopupComponent,
    ],
    entryComponents: [
        PatternColumnGrvComponent,
        PatternColumnGrvDialogComponent,
        PatternColumnGrvPopupComponent,
        PatternColumnGrvDeleteDialogComponent,
        PatternColumnGrvDeletePopupComponent,
    ],
    providers: [
        PatternColumnGrvService,
        PatternColumnGrvPopupService,
        PatternColumnGrvResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationPatternColumnGrvModule {}
