import {BaseEntity} from './../../shared';

export class LocationGrv implements BaseEntity {
    constructor(
        public id?: number,
        public externalid?: string,
        public createdDate?: any,
        public coords?: string,
        public sourceId?: number,
        public items?: BaseEntity[],
        public images?: BaseEntity[],
    ) {
    }
}
