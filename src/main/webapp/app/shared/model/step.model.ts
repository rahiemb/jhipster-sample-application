import { IUser } from 'app/core/user/user.model';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { IAttachment } from 'app/shared/model/attachment.model';
import { IComment } from 'app/shared/model/comment.model';
import { IEmail } from 'app/shared/model/email.model';
import { IAction } from 'app/shared/model/action.model';
import { INotification } from 'app/shared/model/notification.model';
import { IUsers } from 'app/shared/model/users.model';
import { IWorkflow } from 'app/shared/model/workflow.model';

export const enum StepType {
    ACKNOWLEDGEMENT = 'ACKNOWLEDGEMENT',
    DRAFT = 'DRAFT',
    LOCK = 'LOCK',
    REVIEW = 'REVIEW',
    PUBLISH = 'PUBLISH',
    RETIRE = 'RETIRE',
    SIGNATURE = 'SIGNATURE'
}

export interface IStep {
    id?: number;
    manager?: string;
    workflow?: string;
    type?: StepType;
    attachments?: string;
    sender?: IUser;
    groups?: IUserGroup[];
    attachments?: IAttachment[];
    comments?: IComment[];
    emails?: IEmail[];
    actions?: IAction[];
    notifications?: INotification[];
    users?: IUsers[];
    workflow?: IWorkflow;
}

export class Step implements IStep {
    constructor(
        public id?: number,
        public manager?: string,
        public workflow?: string,
        public type?: StepType,
        public attachments?: string,
        public sender?: IUser,
        public groups?: IUserGroup[],
        public attachments?: IAttachment[],
        public comments?: IComment[],
        public emails?: IEmail[],
        public actions?: IAction[],
        public notifications?: INotification[],
        public users?: IUsers[],
        public workflow?: IWorkflow
    ) {}
}
