import { IStep } from 'app/shared/model/step.model';

export interface IEmail {
    id?: number;
    value?: string;
    step?: IStep;
}

export class Email implements IEmail {
    constructor(public id?: number, public value?: string, public step?: IStep) {}
}
