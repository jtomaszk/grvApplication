import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {GrvApplicationAreaGrvModule} from './area-grv/area-grv.module';
import {GrvApplicationSourceGrvModule} from './source-grv/source-grv.module';
import {GrvApplicationSourceArchiveGrvModule} from './source-archive-grv/source-archive-grv.module';
import {GrvApplicationGrvItemGrvModule} from './grv-item-grv/grv-item-grv.module';
import {GrvApplicationLocationGrvModule} from './location-grv/location-grv.module';
import {GrvApplicationErrorGrvModule} from './error-grv/error-grv.module';
import {GrvApplicationInputPatternGrvModule} from './input-pattern-grv/input-pattern-grv.module';
import {GrvApplicationPatternColumnGrvModule} from './pattern-column-grv/pattern-column-grv.module';
import {GrvApplicationGrvItemPersonGrvModule} from './grv-item-person-grv/grv-item-person-grv.module';
import {GrvApplicationLocationImageGrvModule} from './location-image-grv/location-image-grv.module';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GrvApplicationAreaGrvModule,
        GrvApplicationSourceGrvModule,
        GrvApplicationSourceArchiveGrvModule,
        GrvApplicationGrvItemGrvModule,
        GrvApplicationLocationGrvModule,
        GrvApplicationErrorGrvModule,
        GrvApplicationInputPatternGrvModule,
        GrvApplicationPatternColumnGrvModule,
        GrvApplicationGrvItemPersonGrvModule,
        GrvApplicationLocationImageGrvModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GrvApplicationEntityModule {}
