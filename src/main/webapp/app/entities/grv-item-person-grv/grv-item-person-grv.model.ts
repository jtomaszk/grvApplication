import { BaseEntity } from './../../shared';

export class GrvItemPersonGrv implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public anotherLastName?: string,
        public startDateString?: string,
        public endDateString?: string,
        public itemId?: number,
    ) {
    }
}
