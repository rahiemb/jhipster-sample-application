/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ManualUpdateComponent } from 'app/entities/manual/manual-update.component';
import { ManualService } from 'app/entities/manual/manual.service';
import { Manual } from 'app/shared/model/manual.model';

describe('Component Tests', () => {
    describe('Manual Management Update Component', () => {
        let comp: ManualUpdateComponent;
        let fixture: ComponentFixture<ManualUpdateComponent>;
        let service: ManualService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [ManualUpdateComponent]
            })
                .overrideTemplate(ManualUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ManualUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ManualService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Manual(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.manual = entity;
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
                    const entity = new Manual();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.manual = entity;
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
