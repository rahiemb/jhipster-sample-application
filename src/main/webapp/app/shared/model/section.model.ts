import { Moment } from 'moment';
import { IManual } from 'app/shared/model/manual.model';
import { ILock } from 'app/shared/model/lock.model';
import { IDocument } from 'app/shared/model/document.model';
import { ILogEntry } from 'app/shared/model/log-entry.model';

export interface ISection {
    id?: number;
    name?: string;
    code?: string;
    seed?: number;
    mask?: string;
    description?: string;
    enabled?: boolean;
    link?: string;
    orderId?: number;
    retired?: boolean;
    retiredDate?: Moment;
    retiredNote?: string;
    manual?: IManual;
    lock?: ILock;
    documents?: IDocument[];
    logs?: ILogEntry[];
}

export class Section implements ISection {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public seed?: number,
        public mask?: string,
        public description?: string,
        public enabled?: boolean,
        public link?: string,
        public orderId?: number,
        public retired?: boolean,
        public retiredDate?: Moment,
        public retiredNote?: string,
        public manual?: IManual,
        public lock?: ILock,
        public documents?: IDocument[],
        public logs?: ILogEntry[]
    ) {
        this.enabled = this.enabled || false;
        this.retired = this.retired || false;
    }
}
