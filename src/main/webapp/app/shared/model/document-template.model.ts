import { IDocumentTemplatePart } from 'app/shared/model/document-template-part.model';
import { IManual } from 'app/shared/model/manual.model';

export interface IDocumentTemplate {
    id?: number;
    name?: string;
    primaryHeader?: string;
    primaryFooter?: string;
    appendixHeader?: string;
    appendixFooter?: string;
    enabled?: boolean;
    sections?: IDocumentTemplatePart[];
    manual?: IManual;
}

export class DocumentTemplate implements IDocumentTemplate {
    constructor(
        public id?: number,
        public name?: string,
        public primaryHeader?: string,
        public primaryFooter?: string,
        public appendixHeader?: string,
        public appendixFooter?: string,
        public enabled?: boolean,
        public sections?: IDocumentTemplatePart[],
        public manual?: IManual
    ) {
        this.enabled = this.enabled || false;
    }
}
