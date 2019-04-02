/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ManualDetailComponent } from 'app/entities/manual/manual-detail.component';
import { Manual } from 'app/shared/model/manual.model';

describe('Component Tests', () => {
    describe('Manual Management Detail Component', () => {
        let comp: ManualDetailComponent;
        let fixture: ComponentFixture<ManualDetailComponent>;
        const route = ({ data: of({ manual: new Manual(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [ManualDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ManualDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ManualDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.manual).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
