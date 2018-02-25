import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GrvApplicationSharedModule } from '../../shared';
import {
    GrvItemPersonGrvService,
    GrvItemPersonGrvPopupService,
    GrvItemPersonGrvComponent,
    GrvItemPersonGrvDetailComponent,
    GrvItemPersonGrvDialogComponent,
    GrvItemPersonGrvPopupComponent,
    GrvItemPersonGrvDeletePopupComponent,
    GrvItemPersonGrvDeleteDialogComponent,
    grvItemPersonRoute,
    grvItemPersonPopupRoute,
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
