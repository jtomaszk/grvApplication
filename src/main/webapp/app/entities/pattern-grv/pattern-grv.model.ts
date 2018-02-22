import { BaseEntity } from './../../shared';

export class PatternGrv implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public sources?: BaseEntity[],
        public columns?: BaseEntity[],
    ) {
    }
}
