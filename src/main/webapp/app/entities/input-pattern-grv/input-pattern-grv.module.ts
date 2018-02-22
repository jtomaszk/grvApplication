import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrvApplicationSharedModule } from '../../shared';
import {
    InputPatternGrvService,
    InputPatternGrvPopupService,
    InputPatternGrvComponent,
    InputPatternGrvDetailComponent,
    InputPatternGrvDialogComponent,
    InputPatternGrvPopupComponent,
    InputPatternGrvDeletePopupComponent,
    InputPatternGrvDeleteDialogComponent,
    inputPatternRoute,
    inputPatternPopupRoute,
    InputPatternGrvResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...inputPatternRoute,
    ...inputPatternPopupRoute,
];

@NgModule({
    imports: [
        GrvApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InputPatternGrvComponent,
        InputPatternGrvDetailComponent,
        InputPatternGrvDialogComponent,
        InputPatternGrvDeleteDialogComponent,
        InputPatternGrvPopupComponent,
        InputPatternGrvDeletePopupComponent,
    ],
    entryComponents: [
        InputPatternGrvComponent,
        InputPatternGrvDialogComponent,
        InputPatternGrvPopupComponent,
        InputPatternGrvDeleteDialogComponent,
        InputPatternGrvDeletePopupComponent,
    ],
    providers: [
        InputPatternGrvService,
        InputPatternGrvPopupService,
        InputPatternGrvResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationInputPatternGrvModule {}
