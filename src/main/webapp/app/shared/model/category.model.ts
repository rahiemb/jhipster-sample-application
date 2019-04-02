export interface ICategory {
    id?: number;
    value?: string;
    enabled?: boolean;
}

export class Category implements ICategory {
    constructor(public id?: number, public value?: string, public enabled?: boolean) {
        this.enabled = this.enabled || false;
    }
}
