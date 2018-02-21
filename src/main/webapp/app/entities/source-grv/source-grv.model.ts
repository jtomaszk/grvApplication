import { BaseEntity } from './../../shared';

export const enum SourceStatus {
    'OK',
    'ERROR',
    'NEW'
}

export class SourceGrv implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public url?: string,
        public status?: SourceStatus,
        public lastRunDate?: any,
        public info?: string,
        public areaId?: number,
        public patternId?: number,
        public archives?: BaseEntity[],
        public locations?: BaseEntity[],
    ) {
    }
}
