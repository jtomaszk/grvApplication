import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {GrvApplicationSharedModule} from '../../shared';
import {
    InputPatternGrvComponent,
    InputPatternGrvDeleteDialogComponent,
    InputPatternGrvDeletePopupComponent,
    InputPatternGrvDetailComponent,
    InputPatternGrvDialogComponent,
    InputPatternGrvPopupComponent,
    InputPatternGrvPopupService,
    InputPatternGrvResolvePagingParams,
    InputPatternGrvService,
    inputPatternPopupRoute,
    inputPatternRoute,
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
