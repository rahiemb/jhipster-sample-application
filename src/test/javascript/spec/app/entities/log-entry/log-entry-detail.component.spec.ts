/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LogEntryDetailComponent } from 'app/entities/log-entry/log-entry-detail.component';
import { LogEntry } from 'app/shared/model/log-entry.model';

describe('Component Tests', () => {
    describe('LogEntry Management Detail Component', () => {
        let comp: LogEntryDetailComponent;
        let fixture: ComponentFixture<LogEntryDetailComponent>;
        const route = ({ data: of({ logEntry: new LogEntry(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [LogEntryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LogEntryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LogEntryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.logEntry).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
