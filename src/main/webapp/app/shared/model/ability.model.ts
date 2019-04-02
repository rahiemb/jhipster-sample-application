import { IUsers } from 'app/shared/model/users.model';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { IRole } from 'app/shared/model/role.model';

export const enum AbilityAction {
    VIEW = 'VIEW',
    EDIT = 'EDIT',
    PUBLISH = 'PUBLISH'
}

export const enum AbilityScope {
    ALLOW = 'ALLOW',
    DENY = 'DENY'
}

export interface IAbility {
    id?: number;
    resourceId?: number;
    action?: AbilityAction;
    scope?: AbilityScope;
    users?: IUsers[];
    groups?: IUserGroup[];
    roles?: IRole[];
}

export class Ability implements IAbility {
    constructor(
        public id?: number,
        public resourceId?: number,
        public action?: AbilityAction,
        public scope?: AbilityScope,
        public users?: IUsers[],
        public groups?: IUserGroup[],
        public roles?: IRole[]
    ) {}
}
