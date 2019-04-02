import { Moment } from 'moment';
import { INotification } from 'app/shared/model/notification.model';
import { IUsers } from 'app/shared/model/users.model';

export interface IRecipient {
    id?: number;
    notification?: string;
    user?: string;
    sent?: boolean;
    timestamp?: Moment;
    notification?: INotification;
    user?: IUsers;
}

export class Recipient implements IRecipient {
    constructor(
        public id?: number,
        public notification?: string,
        public user?: string,
        public sent?: boolean,
        public timestamp?: Moment,
        public notification?: INotification,
        public user?: IUsers
    ) {
        this.sent = this.sent || false;
    }
}
