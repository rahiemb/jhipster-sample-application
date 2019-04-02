/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DocumentTemplateDetailComponent } from 'app/entities/document-template/document-template-detail.component';
import { DocumentTemplate } from 'app/shared/model/document-template.model';

describe('Component Tests', () => {
    describe('DocumentTemplate Management Detail Component', () => {
        let comp: DocumentTemplateDetailComponent;
        let fixture: ComponentFixture<DocumentTemplateDetailComponent>;
        const route = ({ data: of({ documentTemplate: new DocumentTemplate(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DocumentTemplateDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DocumentTemplateDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocumentTemplateDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.documentTemplate).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
