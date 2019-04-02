import { Moment } from 'moment';
import { IUsers } from 'app/shared/model/users.model';
import { IRecipient } from 'app/shared/model/recipient.model';
import { IStep } from 'app/shared/model/step.model';

export const enum TimeInterval {
    DAYS = 'DAYS',
    WEEKS = 'WEEKS',
    MONTHS = 'MONTHS',
    YEARS = 'YEARS'
}

export interface INotification {
    id?: number;
    value?: number;
    interval?: TimeInterval;
    beforeDate?: Moment;
    afterDate?: Moment;
    sendDate?: Moment;
    sender?: string;
    message?: string;
    sender?: IUsers;
    recipients?: IRecipient[];
    step?: IStep;
}

export class Notification implements INotification {
    constructor(
        public id?: number,
        public value?: number,
        public interval?: TimeInterval,
        public beforeDate?: Moment,
        public afterDate?: Moment,
        public sendDate?: Moment,
        public sender?: string,
        public message?: string,
        public sender?: IUsers,
        public recipients?: IRecipient[],
        public step?: IStep
    ) {}
}
