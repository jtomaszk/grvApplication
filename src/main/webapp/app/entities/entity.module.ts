import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GrvApplicationAreaGrvModule } from './area-grv/area-grv.module';
import { GrvApplicationSourceGrvModule } from './source-grv/source-grv.module';
import { GrvApplicationSourceArchiveGrvModule } from './source-archive-grv/source-archive-grv.module';
import { GrvApplicationGrvItemGrvModule } from './grv-item-grv/grv-item-grv.module';
import { GrvApplicationLocationGrvModule } from './location-grv/location-grv.module';
import { GrvApplicationErrorGrvModule } from './error-grv/error-grv.module';
import { GrvApplicationPatternGrvModule } from './pattern-grv/pattern-grv.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GrvApplicationAreaGrvModule,
        GrvApplicationSourceGrvModule,
        GrvApplicationSourceArchiveGrvModule,
        GrvApplicationGrvItemGrvModule,
        GrvApplicationLocationGrvModule,
        GrvApplicationErrorGrvModule,
        GrvApplicationPatternGrvModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationEntityModule {}
