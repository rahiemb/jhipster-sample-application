/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LockDetailComponent } from 'app/entities/lock/lock-detail.component';
import { Lock } from 'app/shared/model/lock.model';

describe('Component Tests', () => {
    describe('Lock Management Detail Component', () => {
        let comp: LockDetailComponent;
        let fixture: ComponentFixture<LockDetailComponent>;
        const route = ({ data: of({ lock: new Lock(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [LockDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LockDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LockDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lock).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
