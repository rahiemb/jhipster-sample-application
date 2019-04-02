/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DocumentTemplatePartDeleteDialogComponent } from 'app/entities/document-template-part/document-template-part-delete-dialog.component';
import { DocumentTemplatePartService } from 'app/entities/document-template-part/document-template-part.service';

describe('Component Tests', () => {
    describe('DocumentTemplatePart Management Delete Component', () => {
        let comp: DocumentTemplatePartDeleteDialogComponent;
        let fixture: ComponentFixture<DocumentTemplatePartDeleteDialogComponent>;
        let service: DocumentTemplatePartService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DocumentTemplatePartDeleteDialogComponent]
            })
                .overrideTemplate(DocumentTemplatePartDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocumentTemplatePartDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentTemplatePartService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
