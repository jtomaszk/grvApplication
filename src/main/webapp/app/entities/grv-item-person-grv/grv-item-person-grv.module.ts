import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {GrvApplicationSharedModule} from '../../shared';
import {
    GrvItemPersonGrvComponent,
    GrvItemPersonGrvDeleteDialogComponent,
    GrvItemPersonGrvDeletePopupComponent,
    GrvItemPersonGrvDetailComponent,
    GrvItemPersonGrvDialogComponent,
    GrvItemPersonGrvPopupComponent,
    GrvItemPersonGrvPopupService,
    GrvItemPersonGrvService,
    grvItemPersonPopupRoute,
    grvItemPersonRoute,
} from './';

const ENTITY_STATES = [
    ...grvItemPersonRoute,
    ...grvItemPersonPopupRoute,
];

@NgModule({
    imports: [
        GrvApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GrvItemPersonGrvComponent,
        GrvItemPersonGrvDetailComponent,
        GrvItemPersonGrvDialogComponent,
        GrvItemPersonGrvDeleteDialogComponent,
        GrvItemPersonGrvPopupComponent,
        GrvItemPersonGrvDeletePopupComponent,
    ],
    entryComponents: [
        GrvItemPersonGrvComponent,
        GrvItemPersonGrvDialogComponent,
        GrvItemPersonGrvPopupComponent,
        GrvItemPersonGrvDeleteDialogComponent,
        GrvItemPersonGrvDeletePopupComponent,
    ],
    providers: [
        GrvItemPersonGrvService,
        GrvItemPersonGrvPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationGrvItemPersonGrvModule {}
