import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from './role.service';
import { IAbility } from 'app/shared/model/ability.model';
import { AbilityService } from 'app/entities/ability';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from 'app/entities/users';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from 'app/entities/user-group';
import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from 'app/entities/organization';

@Component({
    selector: 'jhi-role-update',
    templateUrl: './role-update.component.html'
})
export class RoleUpdateComponent implements OnInit {
    role: IRole;
    isSaving: boolean;

    abilities: IAbility[];

    users: IUsers[];

    usergroups: IUserGroup[];

    organizations: IOrganization[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected roleService: RoleService,
        protected abilityService: AbilityService,
        protected usersService: UsersService,
        protected userGroupService: UserGroupService,
        protected organizationService: OrganizationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ role }) => {
            this.role = role;
        });
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
        this.userGroupService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUserGroup[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUserGroup[]>) => response.body)
            )
            .subscribe((res: IUserGroup[]) => (this.usergroups = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.organizationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOrganization[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOrganization[]>) => response.body)
            )
            .subscribe((res: IOrganization[]) => (this.organizations = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.role.id !== undefined) {
            this.subscribeToSaveResponse(this.roleService.update(this.role));
        } else {
            this.subscribeToSaveResponse(this.roleService.create(this.role));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRole>>) {
        result.subscribe((res: HttpResponse<IRole>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAbilityById(index: number, item: IAbility) {
        return item.id;
    }

    trackUsersById(index: number, item: IUsers) {
        return item.id;
    }

    trackUserGroupById(index: number, item: IUserGroup) {
        return item.id;
    }

    trackOrganizationById(index: number, item: IOrganization) {
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
