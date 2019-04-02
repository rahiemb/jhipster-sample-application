import { IDocument } from 'app/shared/model/document.model';
import { IVersionSummary } from 'app/shared/model/version-summary.model';
import { IPart } from 'app/shared/model/part.model';
import { IWorkflow } from 'app/shared/model/workflow.model';

export interface IVersion {
    id?: number;
    versionMajor?: number;
    versionMinor?: number;
    versionDraft?: number;
    published?: boolean;
    document?: IDocument;
    summary?: IVersionSummary;
    parts?: IPart[];
    workflows?: IWorkflow[];
}

export class Version implements IVersion {
    constructor(
        public id?: number,
        public versionMajor?: number,
        public versionMinor?: number,
        public versionDraft?: number,
        public published?: boolean,
        public document?: IDocument,
        public summary?: IVersionSummary,
        public parts?: IPart[],
        public workflows?: IWorkflow[]
    ) {
        this.published = this.published || false;
    }
}
