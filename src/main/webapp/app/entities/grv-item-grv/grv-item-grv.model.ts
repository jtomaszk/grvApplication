import { BaseEntity } from './../../shared';

export class GrvItemGrv implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public anotherLastName?: string,
        public startDateString?: string,
        public startDate?: any,
        public endDateString?: string,
        public endDate?: any,
        public validToDateString?: string,
        public validToDate?: any,
        public externalid?: string,
        public info?: string,
        public docnr?: string,
        public createdDate?: any,
        public sourceId?: number,
        public locationId?: number,
    ) {
    }
}
