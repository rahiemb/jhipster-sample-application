import { IUsers } from 'app/shared/model/users.model';

export const enum BookmarkType {
    ORGANIZATION = 'ORGANIZATION',
    MANUAL = 'MANUAL',
    SECTION = 'SECTION',
    DOCUMENT = 'DOCUMENT',
    PART = 'PART'
}

export interface IBookmark {
    id?: number;
    type?: BookmarkType;
    users?: IUsers;
}

export class Bookmark implements IBookmark {
    constructor(public id?: number, public type?: BookmarkType, public users?: IUsers) {}
}
