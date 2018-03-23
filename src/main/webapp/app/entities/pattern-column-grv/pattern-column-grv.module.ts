import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {GrvApplicationSharedModule} from '../../shared';
import {
    PatternColumnGrvComponent,
    PatternColumnGrvDeleteDialogComponent,
    PatternColumnGrvDeletePopupComponent,
    PatternColumnGrvDetailComponent,
    PatternColumnGrvDialogComponent,
    PatternColumnGrvPopupComponent,
    PatternColumnGrvPopupService,
    PatternColumnGrvResolvePagingParams,
    PatternColumnGrvService,
    patternColumnPopupRoute,
    patternColumnRoute,
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
