/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DocumentTemplateComponent } from 'app/entities/document-template/document-template.component';
import { DocumentTemplateService } from 'app/entities/document-template/document-template.service';
import { DocumentTemplate } from 'app/shared/model/document-template.model';

describe('Component Tests', () => {
    describe('DocumentTemplate Management Component', () => {
        let comp: DocumentTemplateComponent;
        let fixture: ComponentFixture<DocumentTemplateComponent>;
        let service: DocumentTemplateService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DocumentTemplateComponent],
                providers: []
            })
                .overrideTemplate(DocumentTemplateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DocumentTemplateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentTemplateService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DocumentTemplate(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.documentTemplates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
