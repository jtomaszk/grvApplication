import { BaseEntity } from './../../shared';

export const enum Column {
    'FIRST_NAME',
    'LAST_NAME',
    'ANOTHER_LAST_NAME',
    'START_DATE',
    'END_DATE',
    'VALID_TO',
    'EXTERNAL_ID',
    'BOX_EXTERNAL_ID',
    'INFO',
    'DOC_NR'
}

export class PatternColumnGrv implements BaseEntity {
    constructor(
        public id?: number,
        public column?: Column,
        public value?: string,
        public patternId?: number,
    ) {
    }
}
