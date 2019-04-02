import { IOrganization } from 'app/shared/model/organization.model';

export interface IReport {
    id?: number;
    name?: string;
    description?: string;
    organization?: IOrganization;
}

export class Report implements IReport {
    constructor(public id?: number, public name?: string, public description?: string, public organization?: IOrganization) {}
}
