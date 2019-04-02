import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IOrganization } from 'app/shared/model/organization.model';

@Component({
    selector: 'jhi-organization-detail',
    templateUrl: './organization-detail.component.html'
})
export class OrganizationDetailComponent implements OnInit {
    organization: IOrganization;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ organization }) => {
            this.organization = organization;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
