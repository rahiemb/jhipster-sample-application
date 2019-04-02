export const enum LockType {
    CHECKOUT = 'CHECKOUT',
    LOCK = 'LOCK'
}

export const enum ObjectType {
    CATEGORY = 'CATEGORY',
    DOCUMENT = 'DOCUMENT',
    MANUAL = 'MANUAL',
    NOTIFICATION = 'NOTIFICATION',
    ORGANIZATION = 'ORGANIZATION',
    REPORT = 'REPORT',
    SECTION = 'SECTION',
    SECURITY = 'SECURITY',
    SYSTEM = 'SYSTEM',
    USER = 'USER'
}

export interface ILock {
    id?: number;
    objectId?: number;
    lockType?: LockType;
    objectType?: ObjectType;
}

export class Lock implements ILock {
    constructor(public id?: number, public objectId?: number, public lockType?: LockType, public objectType?: ObjectType) {}
}
