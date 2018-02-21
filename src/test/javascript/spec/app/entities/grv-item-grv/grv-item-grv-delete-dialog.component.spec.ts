/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { GrvItemGrvDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/grv-item-grv/grv-item-grv-delete-dialog.component';
import { GrvItemGrvService } from '../../../../../../main/webapp/app/entities/grv-item-grv/grv-item-grv.service';

describe('Component Tests', () => {

    describe('GrvItemGrv Management Delete Component', () => {
        let comp: GrvItemGrvDeleteDialogComponent;
        let fixture: ComponentFixture<GrvItemGrvDeleteDialogComponent>;
        let service: GrvItemGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [GrvItemGrvDeleteDialogComponent],
                providers: [
                    GrvItemGrvService
                ]
            })
            .overrideTemplate(GrvItemGrvDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrvItemGrvDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrvItemGrvService);
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
