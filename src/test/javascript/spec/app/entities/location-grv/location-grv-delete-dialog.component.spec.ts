/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { LocationGrvDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/location-grv/location-grv-delete-dialog.component';
import { LocationGrvService } from '../../../../../../main/webapp/app/entities/location-grv/location-grv.service';

describe('Component Tests', () => {

    describe('LocationGrv Management Delete Component', () => {
        let comp: LocationGrvDeleteDialogComponent;
        let fixture: ComponentFixture<LocationGrvDeleteDialogComponent>;
        let service: LocationGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [LocationGrvDeleteDialogComponent],
                providers: [
                    LocationGrvService
                ]
            })
            .overrideTemplate(LocationGrvDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationGrvDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationGrvService);
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
