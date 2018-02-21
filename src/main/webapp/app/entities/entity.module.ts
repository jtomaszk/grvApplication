import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GrvApplicationAreaGrvModule } from './area-grv/area-grv.module';
import { GrvApplicationSourceGrvModule } from './source-grv/source-grv.module';
import { GrvApplicationGrvItemGrvModule } from './grv-item-grv/grv-item-grv.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GrvApplicationAreaGrvModule,
        GrvApplicationSourceGrvModule,
        GrvApplicationGrvItemGrvModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationEntityModule {}
