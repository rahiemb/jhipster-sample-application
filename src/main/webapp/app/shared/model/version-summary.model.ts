export interface IVersionSummary {
    id?: number;
    version?: string;
}

export class VersionSummary implements IVersionSummary {
    constructor(public id?: number, public version?: string) {}
}
