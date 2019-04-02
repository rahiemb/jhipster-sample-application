import { IAbility } from 'app/shared/model/ability.model';
import { IUsers } from 'app/shared/model/users.model';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { IOrganization } from 'app/shared/model/organization.model';

export interface IRole {
    id?: number;
    name?: string;
    abilities?: IAbility[];
    users?: IUsers[];
    groups?: IUserGroup[];
    organizations?: IOrganization[];
}

export class Role implements IRole {
    constructor(
        public id?: number,
        public name?: string,
        public abilities?: IAbility[],
        public users?: IUsers[],
        public groups?: IUserGroup[],
        public organizations?: IOrganization[]
    ) {}
}
