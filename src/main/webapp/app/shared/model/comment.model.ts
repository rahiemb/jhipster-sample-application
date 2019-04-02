import { Moment } from 'moment';
import { IComment } from 'app/shared/model/comment.model';
import { IUsers } from 'app/shared/model/users.model';
import { IDocument } from 'app/shared/model/document.model';
import { IStep } from 'app/shared/model/step.model';

export const enum CommentContext {
    APPENDIX = 'APPENDIX',
    ACKNOWLEDGEMENT = 'ACKNOWLEDGEMENT',
    DOCUMENT = 'DOCUMENT',
    REVIEW = 'REVIEW',
    SECTION = 'SECTION',
    SIGNATURE = 'SIGNATURE'
}

export interface IComment {
    id?: number;
    text?: string;
    context?: CommentContext;
    user?: string;
    timestamp?: Moment;
    replyTo?: string;
    itemId?: string;
    replyTo?: IComment;
    user?: IUsers;
    comment?: IComment;
    replies?: IComment[];
    document?: IDocument;
    step?: IStep;
    users?: IUsers;
}

export class Comment implements IComment {
    constructor(
        public id?: number,
        public text?: string,
        public context?: CommentContext,
        public user?: string,
        public timestamp?: Moment,
        public replyTo?: string,
        public itemId?: string,
        public replyTo?: IComment,
        public user?: IUsers,
        public comment?: IComment,
        public replies?: IComment[],
        public document?: IDocument,
        public step?: IStep,
        public users?: IUsers
    ) {}
}
