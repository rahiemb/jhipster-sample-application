import { IVersion } from 'app/shared/model/version.model';
import { IStep } from 'app/shared/model/step.model';

export const enum WorkflowStatus {
    IN_PROGRESS = 'IN_PROGRESS',
    COMPLETED = 'COMPLETED',
    PAUSED = 'PAUSED'
}

export interface IWorkflow {
    id?: number;
    status?: WorkflowStatus;
    version?: IVersion;
    steps?: IStep[];
}

export class Workflow implements IWorkflow {
    constructor(public id?: number, public status?: WorkflowStatus, public version?: IVersion, public steps?: IStep[]) {}
}
