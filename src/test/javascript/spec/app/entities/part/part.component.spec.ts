/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { PartComponent } from 'app/entities/part/part.component';
import { PartService } from 'app/entities/part/part.service';
import { Part } from 'app/shared/model/part.model';

describe('Component Tests', () => {
    describe('Part Management Component', () => {
        let comp: PartComponent;
        let fixture: ComponentFixture<PartComponent>;
        let service: PartService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [PartComponent],
                providers: []
            })
                .overrideTemplate(PartComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PartComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PartService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Part(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.parts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
