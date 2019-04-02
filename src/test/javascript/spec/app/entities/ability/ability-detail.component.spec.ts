/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { AbilityDetailComponent } from 'app/entities/ability/ability-detail.component';
import { Ability } from 'app/shared/model/ability.model';

describe('Component Tests', () => {
    describe('Ability Management Detail Component', () => {
        let comp: AbilityDetailComponent;
        let fixture: ComponentFixture<AbilityDetailComponent>;
        const route = ({ data: of({ ability: new Ability(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [AbilityDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AbilityDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AbilityDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ability).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
