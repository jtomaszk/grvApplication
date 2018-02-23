import {LOCALE_ID, NgModule} from '@angular/core';
import {Title} from '@angular/platform-browser';
import {registerLocaleData} from '@angular/common';
import locale from '@angular/common/locales/en';

import {GrvApplicationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent} from './';

@NgModule({
    imports: [
        GrvApplicationSharedLibsModule
    ],
    declarations: [
        JhiAlertComponent,
        JhiAlertErrorComponent
    ],
    providers: [
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'en'
        },
    ],
    exports: [
        GrvApplicationSharedLibsModule,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ]
})
export class GrvApplicationSharedCommonModule {
    constructor() {
        registerLocaleData(locale);
    }
}
