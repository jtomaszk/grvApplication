import {BaseEntity} from './../../shared';

export class AreaGrv implements BaseEntity {
    constructor(
        public id?: number,
        public areaName?: string,
        public sources?: BaseEntity[],
    ) {
    }
}
