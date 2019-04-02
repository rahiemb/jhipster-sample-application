import { IStep } from 'app/shared/model/step.model';

export interface IAttachment {
    id?: number;
    link?: string;
    step?: string;
    step?: IStep;
}

export class Attachment implements IAttachment {
    constructor(public id?: number, public link?: string, public step?: string, public step?: IStep) {}
}
