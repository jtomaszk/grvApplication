import { BaseEntity } from './../../shared';

export class LocationImageGrv implements BaseEntity {
    constructor(
        public id?: number,
        public createdDate?: any,
        public imageContentType?: string,
        public image?: any,
        public locationId?: number,
    ) {
    }
}
