/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LockComponent } from 'app/entities/lock/lock.component';
import { LockService } from 'app/entities/lock/lock.service';
import { Lock } from 'app/shared/model/lock.model';

describe('Component Tests', () => {
    describe('Lock Management Component', () => {
        let comp: LockComponent;
        let fixture: ComponentFixture<LockComponent>;
        let service: LockService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [LockComponent],
                providers: []
            })
                .overrideTemplate(LockComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LockComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LockService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Lock(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.locks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
