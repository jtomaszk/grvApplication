import { BaseEntity } from './../../shared';

export class SourceArchiveGrv implements BaseEntity {
    constructor(
        public id?: number,
        public createdDate?: any,
        public json?: any,
        public sourceId?: number,
    ) {
    }
}
