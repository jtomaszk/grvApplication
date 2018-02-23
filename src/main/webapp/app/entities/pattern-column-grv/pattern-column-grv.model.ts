import {BaseEntity} from './../../shared';

export const enum ColumnEnum {
    'FIRST_NAME',
    'LAST_NAME',
    'ANOTHER_LAST_NAME',
    'START_DATE',
    'END_DATE',
    'VALID_TO',
    'EXTERNAL_ID',
    'BOX_EXTERNAL_ID',
    'INFO',
    'DOC_NR',
    'COORDINATES'
}

export class PatternColumnGrv implements BaseEntity {
    constructor(
        public id?: number,
        public column?: ColumnEnum,
        public value?: string,
        public patternId?: number,
    ) {
    }
}
