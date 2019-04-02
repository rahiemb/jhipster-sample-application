/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { WorkflowComponent } from 'app/entities/workflow/workflow.component';
import { WorkflowService } from 'app/entities/workflow/workflow.service';
import { Workflow } from 'app/shared/model/workflow.model';

describe('Component Tests', () => {
    describe('Workflow Management Component', () => {
        let comp: WorkflowComponent;
        let fixture: ComponentFixture<WorkflowComponent>;
        let service: WorkflowService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [WorkflowComponent],
                providers: []
            })
                .overrideTemplate(WorkflowComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WorkflowComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkflowService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Workflow(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.workflows[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
