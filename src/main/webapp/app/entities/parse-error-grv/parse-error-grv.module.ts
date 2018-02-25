import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrvApplicationSharedModule } from '../../shared';
import {
    ParseErrorGrvService,
    ParseErrorGrvPopupService,
    ParseErrorGrvComponent,
    ParseErrorGrvDetailComponent,
    ParseErrorGrvDialogComponent,
    ParseErrorGrvPopupComponent,
    ParseErrorGrvDeletePopupComponent,
    ParseErrorGrvDeleteDialogComponent,
    parseErrorRoute,
    parseErrorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...parseErrorRoute,
    ...parseErrorPopupRoute,
];

@NgModule({
    imports: [
        GrvApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ParseErrorGrvComponent,
        ParseErrorGrvDetailComponent,
        ParseErrorGrvDialogComponent,
        ParseErrorGrvDeleteDialogComponent,
        ParseErrorGrvPopupComponent,
        ParseErrorGrvDeletePopupComponent,
    ],
    entryComponents: [
        ParseErrorGrvComponent,
        ParseErrorGrvDialogComponent,
        ParseErrorGrvPopupComponent,
        ParseErrorGrvDeleteDialogComponent,
        ParseErrorGrvDeletePopupComponent,
    ],
    providers: [
        ParseErrorGrvService,
        ParseErrorGrvPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationParseErrorGrvModule {}
