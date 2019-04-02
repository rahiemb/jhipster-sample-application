import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IVersion } from 'app/shared/model/version.model';
import { IStep } from 'app/shared/model/step.model';

export interface IAction {
    id?: number;
    step?: string;
    recipient?: string;
    notified?: boolean;
    originationTimestamp?: Moment;
    responseTimestamp?: Moment;
    recipient?: IUser;
    version?: IVersion;
    step?: IStep;
}

export class Action implements IAction {
    constructor(
        public id?: number,
        public step?: string,
        public recipient?: string,
        public notified?: boolean,
        public originationTimestamp?: Moment,
        public responseTimestamp?: Moment,
        public recipient?: IUser,
        public version?: IVersion,
        public step?: IStep
    ) {
        this.notified = this.notified || false;
    }
}
