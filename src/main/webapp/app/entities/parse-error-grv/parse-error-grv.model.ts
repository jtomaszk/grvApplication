import { BaseEntity } from './../../shared';

export class ParseErrorGrv implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public msg?: any,
        public createdDate?: any,
        public sourceId?: number,
        public itemId?: number,
    ) {
    }
}
