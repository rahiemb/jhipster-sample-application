/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ManualTypeDetailComponent } from 'app/entities/manual-type/manual-type-detail.component';
import { ManualType } from 'app/shared/model/manual-type.model';

describe('Component Tests', () => {
    describe('ManualType Management Detail Component', () => {
        let comp: ManualTypeDetailComponent;
        let fixture: ComponentFixture<ManualTypeDetailComponent>;
        const route = ({ data: of({ manualType: new ManualType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [ManualTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ManualTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ManualTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.manualType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
