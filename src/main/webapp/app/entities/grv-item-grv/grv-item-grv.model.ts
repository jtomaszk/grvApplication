import { BaseEntity } from './../../shared';

export class GrvItemGrv implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public endDate?: any,
        public validToDateString?: string,
        public validToDate?: any,
        public externalid?: string,
        public info?: any,
        public docnr?: string,
        public createdDate?: any,
        public sourceId?: number,
        public locationId?: number,
        public sourceArchiveId?: number,
        public personId?: number,
        public errors?: BaseEntity[],
    ) {
    }
}
