import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from './users.service';
import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from 'app/entities/organization';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from 'app/entities/user-group';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';
import { IAbility } from 'app/shared/model/ability.model';
import { AbilityService } from 'app/entities/ability';

@Component({
    selector: 'jhi-users-update',
    templateUrl: './users-update.component.html'
})
export class UsersUpdateComponent implements OnInit {
    users: IUsers;
    isSaving: boolean;

    proxies: IUsers[];

    organizations: IOrganization[];

    usergroups: IUserGroup[];

    roles: IRole[];

    abilities: IAbility[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected usersService: UsersService,
        protected organizationService: OrganizationService,
        protected userGroupService: UserGroupService,
        protected roleService: RoleService,
        protected abilityService: AbilityService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ users }) => {
            this.users = users;
        });
        this.usersService
            .query({ filter: 'users-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IUsers[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUsers[]>) => response.body)
            )
            .subscribe(
                (res: IUsers[]) => {
                    if (!this.users.proxy || !this.users.proxy.id) {
                        this.proxies = res;
                    } else {
                        this.usersService
                            .find(this.users.proxy.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IUsers>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IUsers>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IUsers) => (this.proxies = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.organizationService
            .query({ filter: 'users-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IOrganization[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOrganization[]>) => response.body)
            )
            .subscribe(
                (res: IOrganization[]) => {
                    if (!this.users.organization || !this.users.organization.id) {
                        this.organizations = res;
                    } else {
                        this.organizationService
                            .find(this.users.organization.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IOrganization>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IOrganization>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IOrganization) => (this.organizations = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.userGroupService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUserGroup[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUserGroup[]>) => response.body)
            )
            .subscribe((res: IUserGroup[]) => (this.usergroups = res), (res: HttpErrorResponse) => this.onError(res.message));
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
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.users.id !== undefined) {
            this.subscribeToSaveResponse(this.usersService.update(this.users));
        } else {
            this.subscribeToSaveResponse(this.usersService.create(this.users));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsers>>) {
        result.subscribe((res: HttpResponse<IUsers>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUsersById(index: number, item: IUsers) {
        return item.id;
    }

    trackOrganizationById(index: number, item: IOrganization) {
        return item.id;
    }

    trackUserGroupById(index: number, item: IUserGroup) {
        return item.id;
    }

    trackRoleById(index: number, item: IRole) {
        return item.id;
    }

    trackAbilityById(index: number, item: IAbility) {
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
