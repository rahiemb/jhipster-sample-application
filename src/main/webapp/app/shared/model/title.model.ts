import { IUsers } from 'app/shared/model/users.model';

export interface ITitle {
    id?: number;
    value?: string;
    enabled?: boolean;
    users?: IUsers;
}

export class Title implements ITitle {
    constructor(public id?: number, public value?: string, public enabled?: boolean, public users?: IUsers) {
        this.enabled = this.enabled || false;
    }
}
