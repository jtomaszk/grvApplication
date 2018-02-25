/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { ParseErrorGrvDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/parse-error-grv/parse-error-grv-delete-dialog.component';
import { ParseErrorGrvService } from '../../../../../../main/webapp/app/entities/parse-error-grv/parse-error-grv.service';

describe('Component Tests', () => {

    describe('ParseErrorGrv Management Delete Component', () => {
        let comp: ParseErrorGrvDeleteDialogComponent;
        let fixture: ComponentFixture<ParseErrorGrvDeleteDialogComponent>;
        let service: ParseErrorGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [ParseErrorGrvDeleteDialogComponent],
                providers: [
                    ParseErrorGrvService
                ]
            })
            .overrideTemplate(ParseErrorGrvDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParseErrorGrvDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParseErrorGrvService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
