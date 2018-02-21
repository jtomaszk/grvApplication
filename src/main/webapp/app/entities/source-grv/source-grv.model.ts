import { BaseEntity } from './../../shared';

export class SourceGrv implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public url?: string,
        public json?: any,
        public areaId?: number,
    ) {
    }
}
