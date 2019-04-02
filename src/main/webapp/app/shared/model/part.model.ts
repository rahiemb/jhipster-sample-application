import { IPart } from 'app/shared/model/part.model';
import { IVersion } from 'app/shared/model/version.model';

export interface IPart {
    id?: number;
    name?: string;
    ancestorId?: number;
    enabled?: boolean;
    children?: IPart;
    version?: IVersion;
}

export class Part implements IPart {
    constructor(
        public id?: number,
        public name?: string,
        public ancestorId?: number,
        public enabled?: boolean,
        public children?: IPart,
        public version?: IVersion
    ) {
        this.enabled = this.enabled || false;
    }
}
