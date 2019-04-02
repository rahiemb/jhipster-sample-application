/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { VersionSummaryDetailComponent } from 'app/entities/version-summary/version-summary-detail.component';
import { VersionSummary } from 'app/shared/model/version-summary.model';

describe('Component Tests', () => {
    describe('VersionSummary Management Detail Component', () => {
        let comp: VersionSummaryDetailComponent;
        let fixture: ComponentFixture<VersionSummaryDetailComponent>;
        const route = ({ data: of({ versionSummary: new VersionSummary(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [VersionSummaryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VersionSummaryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VersionSummaryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.versionSummary).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
