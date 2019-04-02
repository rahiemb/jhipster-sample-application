import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from './user-group.service';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';
import { IAbility } from 'app/shared/model/ability.model';
import { AbilityService } from 'app/entities/ability';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from 'app/entities/users';
import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from 'app/entities/organization';
import { IStep } from 'app/shared/model/step.model';
import { StepService } from 'app/entities/step';

@Component({
    selector: 'jhi-user-group-update',
    templateUrl: './user-group-update.component.html'
})
export class UserGroupUpdateComponent implements OnInit {
    userGroup: IUserGroup;
    isSaving: boolean;

    roles: IRole[];

    abilities: IAbility[];

    users: IUsers[];

    organizations: IOrganization[];

    steps: IStep[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected userGroupService: UserGroupService,
        protected roleService: RoleService,
        protected abilityService: AbilityService,
        protected usersService: UsersService,
        protected organizationService: OrganizationService,
        protected stepService: StepService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userGroup }) => {
            this.userGroup = userGroup;
        });
        this.roleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRole[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRole[]>) => response.body)
            )
            .subscribe((res: IRole[]) => (this.roles = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.abilityService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAbility[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAbility[]>) => response.body)
            )
            .subscribe((res: IAbility[]) => (this.abilities = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.usersService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUsers[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUsers[]>) => response.body)
            )
            .subscribe((res: IUsers[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.organizationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOrganization[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOrganization[]>) => response.body)
            )
            .subscribe((res: IOrganization[]) => (this.organizations = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.stepService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IStep[]>) => mayBeOk.ok),
                map((response: HttpResponse<IStep[]>) => response.body)
            )
            .subscribe((res: IStep[]) => (this.steps = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userGroup.id !== undefined) {
            this.subscribeToSaveResponse(this.userGroupService.update(this.userGroup));
        } else {
            this.subscribeToSaveResponse(this.userGroupService.create(this.userGroup));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserGroup>>) {
        result.subscribe((res: HttpResponse<IUserGroup>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRoleById(index: number, item: IRole) {
        return item.id;
    }

    trackAbilityById(index: number, item: IAbility) {
        return item.id;
    }

    trackUsersById(index: number, item: IUsers) {
        return item.id;
    }

    trackOrganizationById(index: number, item: IOrganization) {
        return item.id;
    }

    trackStepById(index: number, item: IStep) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
