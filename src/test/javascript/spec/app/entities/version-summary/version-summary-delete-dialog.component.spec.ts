/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { VersionSummaryDeleteDialogComponent } from 'app/entities/version-summary/version-summary-delete-dialog.component';
import { VersionSummaryService } from 'app/entities/version-summary/version-summary.service';

describe('Component Tests', () => {
    describe('VersionSummary Management Delete Component', () => {
        let comp: VersionSummaryDeleteDialogComponent;
        let fixture: ComponentFixture<VersionSummaryDeleteDialogComponent>;
        let service: VersionSummaryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [VersionSummaryDeleteDialogComponent]
            })
                .overrideTemplate(VersionSummaryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VersionSummaryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VersionSummaryService);
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
