import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {GrvApplicationSharedModule} from '../../shared';
import {
    ParseErrorGrvComponent,
    ParseErrorGrvDeleteDialogComponent,
    ParseErrorGrvDeletePopupComponent,
    ParseErrorGrvDetailComponent,
    ParseErrorGrvDialogComponent,
    ParseErrorGrvPopupComponent,
    ParseErrorGrvPopupService,
    ParseErrorGrvService,
    parseErrorPopupRoute,
    parseErrorRoute,
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
