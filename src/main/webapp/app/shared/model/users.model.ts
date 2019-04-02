import { IUsers } from 'app/shared/model/users.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { ITitle } from 'app/shared/model/title.model';
import { IBookmark } from 'app/shared/model/bookmark.model';
import { IComment } from 'app/shared/model/comment.model';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { IRole } from 'app/shared/model/role.model';
import { IAbility } from 'app/shared/model/ability.model';

export interface IUsers {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    enabled?: boolean;
    uid?: string;
    hopkinsId?: string;
    deferToProxy?: boolean;
    proxy?: IUsers;
    organization?: IOrganization;
    titles?: ITitle[];
    bookmarks?: IBookmark[];
    comments?: IComment[];
    groups?: IUserGroup[];
    groups?: IUserGroup[];
    roles?: IRole[];
    abilities?: IAbility[];
}

export class Users implements IUsers {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public enabled?: boolean,
        public uid?: string,
        public hopkinsId?: string,
        public deferToProxy?: boolean,
        public proxy?: IUsers,
        public organization?: IOrganization,
        public titles?: ITitle[],
        public bookmarks?: IBookmark[],
        public comments?: IComment[],
        public groups?: IUserGroup[],
        public groups?: IUserGroup[],
        public roles?: IRole[],
        public abilities?: IAbility[]
    ) {
        this.enabled = this.enabled || false;
        this.deferToProxy = this.deferToProxy || false;
    }
}
