/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ManualTypeComponent } from 'app/entities/manual-type/manual-type.component';
import { ManualTypeService } from 'app/entities/manual-type/manual-type.service';
import { ManualType } from 'app/shared/model/manual-type.model';

describe('Component Tests', () => {
    describe('ManualType Management Component', () => {
        let comp: ManualTypeComponent;
        let fixture: ComponentFixture<ManualTypeComponent>;
        let service: ManualTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [ManualTypeComponent],
                providers: []
            })
                .overrideTemplate(ManualTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ManualTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ManualTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ManualType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.manualTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
