/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { ErrorGrvDialogComponent } from '../../../../../../main/webapp/app/entities/error-grv/error-grv-dialog.component';
import { ErrorGrvService } from '../../../../../../main/webapp/app/entities/error-grv/error-grv.service';
import { ErrorGrv } from '../../../../../../main/webapp/app/entities/error-grv/error-grv.model';
import { SourceGrvService } from '../../../../../../main/webapp/app/entities/source-grv';
import { GrvItemGrvService } from '../../../../../../main/webapp/app/entities/grv-item-grv';

describe('Component Tests', () => {

    describe('ErrorGrv Management Dialog Component', () => {
        let comp: ErrorGrvDialogComponent;
        let fixture: ComponentFixture<ErrorGrvDialogComponent>;
        let service: ErrorGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [ErrorGrvDialogComponent],
                providers: [
                    SourceGrvService,
                    GrvItemGrvService,
                    ErrorGrvService
                ]
            })
            .overrideTemplate(ErrorGrvDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ErrorGrvDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ErrorGrvService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ErrorGrv(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.error = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'errorListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ErrorGrv();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.error = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'errorListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
