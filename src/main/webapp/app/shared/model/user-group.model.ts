import { IRole } from 'app/shared/model/role.model';
import { IAbility } from 'app/shared/model/ability.model';
import { IUsers } from 'app/shared/model/users.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { IStep } from 'app/shared/model/step.model';

export const enum GroupType {
    ALL = 'ALL',
    PERMISSIONS = 'PERMISSIONS',
    SPONSORS = 'SPONSORS',
    DEVELOPERS = 'DEVELOPERS',
    SIMPLE = 'SIMPLE'
}

export interface IUserGroup {
    id?: number;
    name?: string;
    type?: GroupType;
    ad?: boolean;
    roles?: IRole[];
    abilities?: IAbility[];
    users?: IUsers[];
    organizations?: IOrganization[];
    step?: IStep;
}

export class UserGroup implements IUserGroup {
    constructor(
        public id?: number,
        public name?: string,
        public type?: GroupType,
        public ad?: boolean,
        public roles?: IRole[],
        public abilities?: IAbility[],
        public users?: IUsers[],
        public organizations?: IOrganization[],
        public step?: IStep
    ) {
        this.ad = this.ad || false;
    }
}
