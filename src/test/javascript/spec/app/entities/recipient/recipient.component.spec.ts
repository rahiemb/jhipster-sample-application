/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { RecipientComponent } from 'app/entities/recipient/recipient.component';
import { RecipientService } from 'app/entities/recipient/recipient.service';
import { Recipient } from 'app/shared/model/recipient.model';

describe('Component Tests', () => {
    describe('Recipient Management Component', () => {
        let comp: RecipientComponent;
        let fixture: ComponentFixture<RecipientComponent>;
        let service: RecipientService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [RecipientComponent],
                providers: []
            })
                .overrideTemplate(RecipientComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecipientComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecipientService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Recipient(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.recipients[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
