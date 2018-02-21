import { BaseEntity } from './../../shared';

export class PatternGrv implements BaseEntity {
    constructor(
        public id?: number,
        public columns?: string,
        public values?: string,
    ) {
    }
}
