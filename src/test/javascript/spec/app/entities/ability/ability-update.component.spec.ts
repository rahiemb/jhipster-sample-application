/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AbilityUpdateComponent } from 'app/entities/ability/ability-update.component';
import { AbilityService } from 'app/entities/ability/ability.service';
import { Ability } from 'app/shared/model/ability.model';

describe('Component Tests', () => {
    describe('Ability Management Update Component', () => {
        let comp: AbilityUpdateComponent;
        let fixture: ComponentFixture<AbilityUpdateComponent>;
        let service: AbilityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [AbilityUpdateComponent]
            })
                .overrideTemplate(AbilityUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AbilityUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbilityService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Ability(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ability = entity;
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
                    const entity = new Ability();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ability = entity;
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
