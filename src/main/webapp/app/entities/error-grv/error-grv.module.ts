import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {GrvApplicationSharedModule} from '../../shared';
import {
    ErrorGrvComponent,
    ErrorGrvDeleteDialogComponent,
    ErrorGrvDeletePopupComponent,
    ErrorGrvDetailComponent,
    ErrorGrvDialogComponent,
    ErrorGrvPopupComponent,
    ErrorGrvPopupService,
    ErrorGrvService,
    errorPopupRoute,
    errorRoute,
} from './';

const ENTITY_STATES = [
    ...errorRoute,
    ...errorPopupRoute,
];

@NgModule({
    imports: [
        GrvApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ErrorGrvComponent,
        ErrorGrvDetailComponent,
        ErrorGrvDialogComponent,
        ErrorGrvDeleteDialogComponent,
        ErrorGrvPopupComponent,
        ErrorGrvDeletePopupComponent,
    ],
    entryComponents: [
        ErrorGrvComponent,
        ErrorGrvDialogComponent,
        ErrorGrvPopupComponent,
        ErrorGrvDeleteDialogComponent,
        ErrorGrvDeletePopupComponent,
    ],
    providers: [
        ErrorGrvService,
        ErrorGrvPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationErrorGrvModule {}
