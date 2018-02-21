import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrvApplicationSharedModule } from '../../shared';
import {
    PatternGrvService,
    PatternGrvPopupService,
    PatternGrvComponent,
    PatternGrvDetailComponent,
    PatternGrvDialogComponent,
    PatternGrvPopupComponent,
    PatternGrvDeletePopupComponent,
    PatternGrvDeleteDialogComponent,
    patternRoute,
    patternPopupRoute,
    PatternGrvResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...patternRoute,
    ...patternPopupRoute,
];

@NgModule({
    imports: [
        GrvApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PatternGrvComponent,
        PatternGrvDetailComponent,
        PatternGrvDialogComponent,
        PatternGrvDeleteDialogComponent,
        PatternGrvPopupComponent,
        PatternGrvDeletePopupComponent,
    ],
    entryComponents: [
        PatternGrvComponent,
        PatternGrvDialogComponent,
        PatternGrvPopupComponent,
        PatternGrvDeleteDialogComponent,
        PatternGrvDeletePopupComponent,
    ],
    providers: [
        PatternGrvService,
        PatternGrvPopupService,
        PatternGrvResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationPatternGrvModule {}
