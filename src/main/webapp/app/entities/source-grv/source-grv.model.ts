import { BaseEntity } from './../../shared';

export const enum SourceStatusEnum {
    'OK',
    'ERROR',
    'NEW'
}

export class SourceGrv implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public url?: string,
        public status?: SourceStatusEnum,
        public lastRunDate?: any,
        public info?: string,
        public areaId?: number,
        public patternId?: number,
        public errors?: BaseEntity[],
        public archives?: BaseEntity[],
        public locations?: BaseEntity[],
    ) {
    }
}
