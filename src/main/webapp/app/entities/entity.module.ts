import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'ability',
                loadChildren: './ability/ability.module#JhipsterSampleApplicationAbilityModule'
            },
            {
                path: 'action',
                loadChildren: './action/action.module#JhipsterSampleApplicationActionModule'
            },
            {
                path: 'attachment',
                loadChildren: './attachment/attachment.module#JhipsterSampleApplicationAttachmentModule'
            },
            {
                path: 'bookmark',
                loadChildren: './bookmark/bookmark.module#JhipsterSampleApplicationBookmarkModule'
            },
            {
                path: 'category',
                loadChildren: './category/category.module#JhipsterSampleApplicationCategoryModule'
            },
            {
                path: 'comment',
                loadChildren: './comment/comment.module#JhipsterSampleApplicationCommentModule'
            },
            {
                path: 'document',
                loadChildren: './document/document.module#JhipsterSampleApplicationDocumentModule'
            },
            {
                path: 'document-template',
                loadChildren: './document-template/document-template.module#JhipsterSampleApplicationDocumentTemplateModule'
            },
            {
                path: 'document-template-part',
                loadChildren: './document-template-part/document-template-part.module#JhipsterSampleApplicationDocumentTemplatePartModule'
            },
            {
                path: 'email',
                loadChildren: './email/email.module#JhipsterSampleApplicationEmailModule'
            },
            {
                path: 'user-group',
                loadChildren: './user-group/user-group.module#JhipsterSampleApplicationUserGroupModule'
            },
            {
                path: 'keyword',
                loadChildren: './keyword/keyword.module#JhipsterSampleApplicationKeywordModule'
            },
            {
                path: 'lock',
                loadChildren: './lock/lock.module#JhipsterSampleApplicationLockModule'
            },
            {
                path: 'log-entry',
                loadChildren: './log-entry/log-entry.module#JhipsterSampleApplicationLogEntryModule'
            },
            {
                path: 'manual',
                loadChildren: './manual/manual.module#JhipsterSampleApplicationManualModule'
            },
            {
                path: 'manual-type',
                loadChildren: './manual-type/manual-type.module#JhipsterSampleApplicationManualTypeModule'
            },
            {
                path: 'notification',
                loadChildren: './notification/notification.module#JhipsterSampleApplicationNotificationModule'
            },
            {
                path: 'organization',
                loadChildren: './organization/organization.module#JhipsterSampleApplicationOrganizationModule'
            },
            {
                path: 'part',
                loadChildren: './part/part.module#JhipsterSampleApplicationPartModule'
            },
            {
                path: 'participant',
                loadChildren: './participant/participant.module#JhipsterSampleApplicationParticipantModule'
            },
            {
                path: 'recipient',
                loadChildren: './recipient/recipient.module#JhipsterSampleApplicationRecipientModule'
            },
            {
                path: 'report',
                loadChildren: './report/report.module#JhipsterSampleApplicationReportModule'
            },
            {
                path: 'role',
                loadChildren: './role/role.module#JhipsterSampleApplicationRoleModule'
            },
            {
                path: 'section',
                loadChildren: './section/section.module#JhipsterSampleApplicationSectionModule'
            },
            {
                path: 'step',
                loadChildren: './step/step.module#JhipsterSampleApplicationStepModule'
            },
            {
                path: 'title',
                loadChildren: './title/title.module#JhipsterSampleApplicationTitleModule'
            },
            {
                path: 'users',
                loadChildren: './users/users.module#JhipsterSampleApplicationUsersModule'
            },
            {
                path: 'version',
                loadChildren: './version/version.module#JhipsterSampleApplicationVersionModule'
            },
            {
                path: 'version-summary',
                loadChildren: './version-summary/version-summary.module#JhipsterSampleApplicationVersionSummaryModule'
            },
            {
                path: 'workflow',
                loadChildren: './workflow/workflow.module#JhipsterSampleApplicationWorkflowModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
