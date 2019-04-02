/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ManualComponent } from 'app/entities/manual/manual.component';
import { ManualService } from 'app/entities/manual/manual.service';
import { Manual } from 'app/shared/model/manual.model';

describe('Component Tests', () => {
    describe('Manual Management Component', () => {
        let comp: ManualComponent;
        let fixture: ComponentFixture<ManualComponent>;
        let service: ManualService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [ManualComponent],
                providers: []
            })
                .overrideTemplate(ManualComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ManualComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ManualService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Manual(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.manuals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
