/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { LocationImageGrvDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/location-image-grv/location-image-grv-delete-dialog.component';
import { LocationImageGrvService } from '../../../../../../main/webapp/app/entities/location-image-grv/location-image-grv.service';

describe('Component Tests', () => {

    describe('LocationImageGrv Management Delete Component', () => {
        let comp: LocationImageGrvDeleteDialogComponent;
        let fixture: ComponentFixture<LocationImageGrvDeleteDialogComponent>;
        let service: LocationImageGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [LocationImageGrvDeleteDialogComponent],
                providers: [
                    LocationImageGrvService
                ]
            })
            .overrideTemplate(LocationImageGrvDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationImageGrvDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationImageGrvService);
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
