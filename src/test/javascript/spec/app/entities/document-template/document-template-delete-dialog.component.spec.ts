/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DocumentTemplateDeleteDialogComponent } from 'app/entities/document-template/document-template-delete-dialog.component';
import { DocumentTemplateService } from 'app/entities/document-template/document-template.service';

describe('Component Tests', () => {
    describe('DocumentTemplate Management Delete Component', () => {
        let comp: DocumentTemplateDeleteDialogComponent;
        let fixture: ComponentFixture<DocumentTemplateDeleteDialogComponent>;
        let service: DocumentTemplateService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DocumentTemplateDeleteDialogComponent]
            })
                .overrideTemplate(DocumentTemplateDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocumentTemplateDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentTemplateService);
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
