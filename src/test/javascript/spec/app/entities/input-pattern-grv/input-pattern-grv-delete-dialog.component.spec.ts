/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { InputPatternGrvDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/input-pattern-grv/input-pattern-grv-delete-dialog.component';
import { InputPatternGrvService } from '../../../../../../main/webapp/app/entities/input-pattern-grv/input-pattern-grv.service';

describe('Component Tests', () => {

    describe('InputPatternGrv Management Delete Component', () => {
        let comp: InputPatternGrvDeleteDialogComponent;
        let fixture: ComponentFixture<InputPatternGrvDeleteDialogComponent>;
        let service: InputPatternGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [InputPatternGrvDeleteDialogComponent],
                providers: [
                    InputPatternGrvService
                ]
            })
            .overrideTemplate(InputPatternGrvDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InputPatternGrvDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InputPatternGrvService);
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
