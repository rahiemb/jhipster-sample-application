/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DocumentTemplatePartDetailComponent } from 'app/entities/document-template-part/document-template-part-detail.component';
import { DocumentTemplatePart } from 'app/shared/model/document-template-part.model';

describe('Component Tests', () => {
    describe('DocumentTemplatePart Management Detail Component', () => {
        let comp: DocumentTemplatePartDetailComponent;
        let fixture: ComponentFixture<DocumentTemplatePartDetailComponent>;
        const route = ({ data: of({ documentTemplatePart: new DocumentTemplatePart(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DocumentTemplatePartDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DocumentTemplatePartDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocumentTemplatePartDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.documentTemplatePart).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
