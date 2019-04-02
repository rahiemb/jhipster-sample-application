import { Moment } from 'moment';
import { IDocument } from 'app/shared/model/document.model';
import { IManual } from 'app/shared/model/manual.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { ISection } from 'app/shared/model/section.model';

export const enum ObjectType {
    CATEGORY = 'CATEGORY',
    DOCUMENT = 'DOCUMENT',
    MANUAL = 'MANUAL',
    NOTIFICATION = 'NOTIFICATION',
    ORGANIZATION = 'ORGANIZATION',
    REPORT = 'REPORT',
    SECTION = 'SECTION',
    SECURITY = 'SECURITY',
    SYSTEM = 'SYSTEM',
    USER = 'USER'
}

export const enum LogLevel {
    INFO = 'INFO',
    WARNING = 'WARNING',
    ERROR = 'ERROR'
}

export interface ILogEntry {
    id?: number;
    type?: ObjectType;
    level?: LogLevel;
    objectId?: number;
    details?: string;
    user?: string;
    timestamp?: Moment;
    document?: IDocument;
    manual?: IManual;
    organization?: IOrganization;
    section?: ISection;
}

export class LogEntry implements ILogEntry {
    constructor(
        public id?: number,
        public type?: ObjectType,
        public level?: LogLevel,
        public objectId?: number,
        public details?: string,
        public user?: string,
        public timestamp?: Moment,
        public document?: IDocument,
        public manual?: IManual,
        public organization?: IOrganization,
        public section?: ISection
    ) {}
}
