import { Moment } from 'moment';
import { ILock } from 'app/shared/model/lock.model';
import { IDocumentTemplate } from 'app/shared/model/document-template.model';
import { ISection } from 'app/shared/model/section.model';
import { ILogEntry } from 'app/shared/model/log-entry.model';
import { IManualType } from 'app/shared/model/manual-type.model';
import { IOrganization } from 'app/shared/model/organization.model';

export interface IManual {
    id?: number;
    name?: string;
    code?: string;
    enabled?: boolean;
    link?: string;
    description?: string;
    trackChangesEnabled?: boolean;
    retired?: boolean;
    retiredDate?: Moment;
    retiredNote?: string;
    lock?: ILock;
    defaultTemplate?: IDocumentTemplate;
    sections?: ISection[];
    logs?: ILogEntry[];
    templates?: IDocumentTemplate[];
    manualTypes?: IManualType[];
    organization?: IOrganization;
}

export class Manual implements IManual {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public enabled?: boolean,
        public link?: string,
        public description?: string,
        public trackChangesEnabled?: boolean,
        public retired?: boolean,
        public retiredDate?: Moment,
        public retiredNote?: string,
        public lock?: ILock,
        public defaultTemplate?: IDocumentTemplate,
        public sections?: ISection[],
        public logs?: ILogEntry[],
        public templates?: IDocumentTemplate[],
        public manualTypes?: IManualType[],
        public organization?: IOrganization
    ) {
        this.enabled = this.enabled || false;
        this.trackChangesEnabled = this.trackChangesEnabled || false;
        this.retired = this.retired || false;
    }
}
