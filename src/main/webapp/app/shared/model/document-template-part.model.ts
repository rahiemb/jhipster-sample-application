import { IDocumentTemplate } from 'app/shared/model/document-template.model';

export interface IDocumentTemplatePart {
    id?: number;
    name?: string;
    text?: string;
    readOnly?: boolean;
    documentTemplate?: IDocumentTemplate;
}

export class DocumentTemplatePart implements IDocumentTemplatePart {
    constructor(
        public id?: number,
        public name?: string,
        public text?: string,
        public readOnly?: boolean,
        public documentTemplate?: IDocumentTemplate
    ) {
        this.readOnly = this.readOnly || false;
    }
}
