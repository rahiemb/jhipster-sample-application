export interface IParticipant {
    id?: number;
    value?: string;
    enabled?: boolean;
}

export class Participant implements IParticipant {
    constructor(public id?: number, public value?: string, public enabled?: boolean) {
        this.enabled = this.enabled || false;
    }
}
