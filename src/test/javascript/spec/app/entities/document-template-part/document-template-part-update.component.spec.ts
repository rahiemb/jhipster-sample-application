/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DocumentTemplatePartUpdateComponent } from 'app/entities/document-template-part/document-template-part-update.component';
import { DocumentTemplatePartService } from 'app/entities/document-template-part/document-template-part.service';
import { DocumentTemplatePart } from 'app/shared/model/document-template-part.model';

describe('Component Tests', () => {
    describe('DocumentTemplatePart Management Update Component', () => {
        let comp: DocumentTemplatePartUpdateComponent;
        let fixture: ComponentFixture<DocumentTemplatePartUpdateComponent>;
        let service: DocumentTemplatePartService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DocumentTemplatePartUpdateComponent]
            })
                .overrideTemplate(DocumentTemplatePartUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DocumentTemplatePartUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentTemplatePartService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DocumentTemplatePart(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.documentTemplatePart = entity;
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
                    const entity = new DocumentTemplatePart();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.documentTemplatePart = entity;
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
