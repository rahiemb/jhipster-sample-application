/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { VersionSummaryComponent } from 'app/entities/version-summary/version-summary.component';
import { VersionSummaryService } from 'app/entities/version-summary/version-summary.service';
import { VersionSummary } from 'app/shared/model/version-summary.model';

describe('Component Tests', () => {
    describe('VersionSummary Management Component', () => {
        let comp: VersionSummaryComponent;
        let fixture: ComponentFixture<VersionSummaryComponent>;
        let service: VersionSummaryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [VersionSummaryComponent],
                providers: []
            })
                .overrideTemplate(VersionSummaryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VersionSummaryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VersionSummaryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new VersionSummary(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.versionSummaries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
