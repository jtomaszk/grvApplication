/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { GrvItemPersonGrvDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/grv-item-person-grv/grv-item-person-grv-delete-dialog.component';
import { GrvItemPersonGrvService } from '../../../../../../main/webapp/app/entities/grv-item-person-grv/grv-item-person-grv.service';

describe('Component Tests', () => {

    describe('GrvItemPersonGrv Management Delete Component', () => {
        let comp: GrvItemPersonGrvDeleteDialogComponent;
        let fixture: ComponentFixture<GrvItemPersonGrvDeleteDialogComponent>;
        let service: GrvItemPersonGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [GrvItemPersonGrvDeleteDialogComponent],
                providers: [
                    GrvItemPersonGrvService
                ]
            })
            .overrideTemplate(GrvItemPersonGrvDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrvItemPersonGrvDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrvItemPersonGrvService);
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
