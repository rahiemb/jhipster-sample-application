import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAbility } from 'app/shared/model/ability.model';
import { AbilityService } from './ability.service';
import { IUsers } from 'app/shared/model/users.model';
import { UsersService } from 'app/entities/users';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from 'app/entities/user-group';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';

@Component({
    selector: 'jhi-ability-update',
    templateUrl: './ability-update.component.html'
})
export class AbilityUpdateComponent implements OnInit {
    ability: IAbility;
    isSaving: boolean;

    users: IUsers[];

    usergroups: IUserGroup[];

    roles: IRole[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected abilityService: AbilityService,
        protected usersService: UsersService,
        protected userGroupService: UserGroupService,
        protected roleService: RoleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ability }) => {
            this.ability = ability;
        });
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
        this.roleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRole[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRole[]>) => response.body)
            )
            .subscribe((res: IRole[]) => (this.roles = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ability.id !== undefined) {
            this.subscribeToSaveResponse(this.abilityService.update(this.ability));
        } else {
            this.subscribeToSaveResponse(this.abilityService.create(this.ability));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAbility>>) {
        result.subscribe((res: HttpResponse<IAbility>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserGroupById(index: number, item: IUserGroup) {
        return item.id;
    }

    trackRoleById(index: number, item: IRole) {
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
