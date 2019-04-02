import { Moment } from 'moment';
import { ILock } from 'app/shared/model/lock.model';
import { IWorkflow } from 'app/shared/model/workflow.model';
import { IVersion } from 'app/shared/model/version.model';
import { ILogEntry } from 'app/shared/model/log-entry.model';
import { IComment } from 'app/shared/model/comment.model';
import { IKeyword } from 'app/shared/model/keyword.model';
import { ICategory } from 'app/shared/model/category.model';
import { IParticipant } from 'app/shared/model/participant.model';
import { ISection } from 'app/shared/model/section.model';

export const enum ExpirationType {
    DATE = 'DATE',
    INTERVAL = 'INTERVAL'
}

export const enum TimeInterval {
    DAYS = 'DAYS',
    WEEKS = 'WEEKS',
    MONTHS = 'MONTHS',
    YEARS = 'YEARS'
}

export const enum ExpirationBase {
    EFFECTIVEDATE = 'EFFECTIVEDATE',
    APPROVALDATE = 'APPROVALDATE',
    SUPERSEDESDATE = 'SUPERSEDESDATE',
    ORIGINALDATE = 'ORIGINALDATE',
    REVISIONDATE = 'REVISIONDATE',
    REVIEWDATE = 'REVIEWDATE'
}

export interface IDocument {
    id?: number;
    name?: string;
    code?: string;
    seed?: number;
    description?: string;
    enabled?: boolean;
    link?: string;
    orderId?: number;
    effectiveDate?: Moment;
    approvalDate?: Moment;
    supersedesDate?: Moment;
    originalDate?: Moment;
    reviewDate?: Moment;
    revisionDate?: Moment;
    expirationType?: ExpirationType;
    expirationDate?: Moment;
    expirationPeriod?: number;
    expirationInterval?: TimeInterval;
    expirationBase?: ExpirationBase;
    tableOfContents?: boolean;
    retired?: boolean;
    retiredDate?: Moment;
    retiredNote?: string;
    lock?: ILock;
    workflow?: IWorkflow;
    versions?: IVersion[];
    logs?: ILogEntry[];
    comments?: IComment[];
    keywords?: IKeyword[];
    categories?: ICategory[];
    participants?: IParticipant[];
    section?: ISection;
}

export class Document implements IDocument {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public seed?: number,
        public description?: string,
        public enabled?: boolean,
        public link?: string,
        public orderId?: number,
        public effectiveDate?: Moment,
        public approvalDate?: Moment,
        public supersedesDate?: Moment,
        public originalDate?: Moment,
        public reviewDate?: Moment,
        public revisionDate?: Moment,
        public expirationType?: ExpirationType,
        public expirationDate?: Moment,
        public expirationPeriod?: number,
        public expirationInterval?: TimeInterval,
        public expirationBase?: ExpirationBase,
        public tableOfContents?: boolean,
        public retired?: boolean,
        public retiredDate?: Moment,
        public retiredNote?: string,
        public lock?: ILock,
        public workflow?: IWorkflow,
        public versions?: IVersion[],
        public logs?: ILogEntry[],
        public comments?: IComment[],
        public keywords?: IKeyword[],
        public categories?: ICategory[],
        public participants?: IParticipant[],
        public section?: ISection
    ) {
        this.enabled = this.enabled || false;
        this.tableOfContents = this.tableOfContents || false;
        this.retired = this.retired || false;
    }
}
