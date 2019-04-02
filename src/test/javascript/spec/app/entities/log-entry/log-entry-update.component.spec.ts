/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LogEntryUpdateComponent } from 'app/entities/log-entry/log-entry-update.component';
import { LogEntryService } from 'app/entities/log-entry/log-entry.service';
import { LogEntry } from 'app/shared/model/log-entry.model';

describe('Component Tests', () => {
    describe('LogEntry Management Update Component', () => {
        let comp: LogEntryUpdateComponent;
        let fixture: ComponentFixture<LogEntryUpdateComponent>;
        let service: LogEntryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [LogEntryUpdateComponent]
            })
                .overrideTemplate(LogEntryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LogEntryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogEntryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new LogEntry(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.logEntry = entity;
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
                    const entity = new LogEntry();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.logEntry = entity;
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
