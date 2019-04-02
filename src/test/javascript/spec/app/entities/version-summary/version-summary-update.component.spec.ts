/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { VersionSummaryUpdateComponent } from 'app/entities/version-summary/version-summary-update.component';
import { VersionSummaryService } from 'app/entities/version-summary/version-summary.service';
import { VersionSummary } from 'app/shared/model/version-summary.model';

describe('Component Tests', () => {
    describe('VersionSummary Management Update Component', () => {
        let comp: VersionSummaryUpdateComponent;
        let fixture: ComponentFixture<VersionSummaryUpdateComponent>;
        let service: VersionSummaryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [VersionSummaryUpdateComponent]
            })
                .overrideTemplate(VersionSummaryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VersionSummaryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VersionSummaryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new VersionSummary(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.versionSummary = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new VersionSummary();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.versionSummary = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
