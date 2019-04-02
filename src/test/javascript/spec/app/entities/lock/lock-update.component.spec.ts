/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { LockUpdateComponent } from 'app/entities/lock/lock-update.component';
import { LockService } from 'app/entities/lock/lock.service';
import { Lock } from 'app/shared/model/lock.model';

describe('Component Tests', () => {
    describe('Lock Management Update Component', () => {
        let comp: LockUpdateComponent;
        let fixture: ComponentFixture<LockUpdateComponent>;
        let service: LockService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [LockUpdateComponent]
            })
                .overrideTemplate(LockUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LockUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LockService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Lock(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lock = entity;
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
                    const entity = new Lock();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lock = entity;
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
