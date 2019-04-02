/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AbilityComponent } from 'app/entities/ability/ability.component';
import { AbilityService } from 'app/entities/ability/ability.service';
import { Ability } from 'app/shared/model/ability.model';

describe('Component Tests', () => {
    describe('Ability Management Component', () => {
        let comp: AbilityComponent;
        let fixture: ComponentFixture<AbilityComponent>;
        let service: AbilityService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [AbilityComponent],
                providers: []
            })
                .overrideTemplate(AbilityComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AbilityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbilityService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Ability(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.abilities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
