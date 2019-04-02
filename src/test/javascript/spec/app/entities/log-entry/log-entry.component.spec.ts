/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LogEntryComponent } from 'app/entities/log-entry/log-entry.component';
import { LogEntryService } from 'app/entities/log-entry/log-entry.service';
import { LogEntry } from 'app/shared/model/log-entry.model';

describe('Component Tests', () => {
    describe('LogEntry Management Component', () => {
        let comp: LogEntryComponent;
        let fixture: ComponentFixture<LogEntryComponent>;
        let service: LogEntryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [LogEntryComponent],
                providers: []
            })
                .overrideTemplate(LogEntryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LogEntryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogEntryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new LogEntry(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.logEntries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
