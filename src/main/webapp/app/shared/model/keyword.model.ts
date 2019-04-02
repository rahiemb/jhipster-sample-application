export interface IKeyword {
    id?: number;
    value?: string;
    synonyms?: string;
}

export class Keyword implements IKeyword {
    constructor(public id?: number, public value?: string, public synonyms?: string) {}
}
