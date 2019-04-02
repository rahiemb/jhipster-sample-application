export interface IManualType {
    id?: number;
    value?: string;
    enabled?: boolean;
}

export class ManualType implements IManualType {
    constructor(public id?: number, public value?: string, public enabled?: boolean) {
        this.enabled = this.enabled || false;
    }
}
