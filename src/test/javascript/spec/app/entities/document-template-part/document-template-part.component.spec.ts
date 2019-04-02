/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DocumentTemplatePartComponent } from 'app/entities/document-template-part/document-template-part.component';
import { DocumentTemplatePartService } from 'app/entities/document-template-part/document-template-part.service';
import { DocumentTemplatePart } from 'app/shared/model/document-template-part.model';

describe('Component Tests', () => {
    describe('DocumentTemplatePart Management Component', () => {
        let comp: DocumentTemplatePartComponent;
        let fixture: ComponentFixture<DocumentTemplatePartComponent>;
        let service: DocumentTemplatePartService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DocumentTemplatePartComponent],
                providers: []
            })
                .overrideTemplate(DocumentTemplatePartComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DocumentTemplatePartComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentTemplatePartService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DocumentTemplatePart(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.documentTemplateParts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
