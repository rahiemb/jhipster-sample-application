import { IManual } from 'app/shared/model/manual.model';
import { ILogEntry } from 'app/shared/model/log-entry.model';
import { IReport } from 'app/shared/model/report.model';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { IRole } from 'app/shared/model/role.model';

export interface IOrganization {
    id?: number;
    name?: string;
    enabled?: boolean;
    orderId?: number;
    siteUrl?: string;
    repository?: string;
    thumbnailContentType?: string;
    thumbnail?: any;
    sorlCore?: string;
    approvalDateEnabled?: boolean;
    effectiveDateEnabled?: boolean;
    originalDateEnabled?: boolean;
    reviewDateEnabled?: boolean;
    revisionDateEnabled?: boolean;
    supersedesDateEnabled?: boolean;
    manuals?: IManual[];
    logs?: ILogEntry[];
    reports?: IReport[];
    groups?: IUserGroup[];
    roles?: IRole[];
}

export class Organization implements IOrganization {
    constructor(
        public id?: number,
        public name?: string,
        public enabled?: boolean,
        public orderId?: number,
        public siteUrl?: string,
        public repository?: string,
        public thumbnailContentType?: string,
        public thumbnail?: any,
        public sorlCore?: string,
        public approvalDateEnabled?: boolean,
        public effectiveDateEnabled?: boolean,
        public originalDateEnabled?: boolean,
        public reviewDateEnabled?: boolean,
        public revisionDateEnabled?: boolean,
        public supersedesDateEnabled?: boolean,
        public manuals?: IManual[],
        public logs?: ILogEntry[],
        public reports?: IReport[],
        public groups?: IUserGroup[],
        public roles?: IRole[]
    ) {
        this.enabled = this.enabled || false;
        this.approvalDateEnabled = this.approvalDateEnabled || false;
        this.effectiveDateEnabled = this.effectiveDateEnabled || false;
        this.originalDateEnabled = this.originalDateEnabled || false;
        this.reviewDateEnabled = this.reviewDateEnabled || false;
        this.revisionDateEnabled = this.revisionDateEnabled || false;
        this.supersedesDateEnabled = this.supersedesDateEnabled || false;
    }
}
