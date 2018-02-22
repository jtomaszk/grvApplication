/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { GrvItemPersonGrvDialogComponent } from '../../../../../../main/webapp/app/entities/grv-item-person-grv/grv-item-person-grv-dialog.component';
import { GrvItemPersonGrvService } from '../../../../../../main/webapp/app/entities/grv-item-person-grv/grv-item-person-grv.service';
import { GrvItemPersonGrv } from '../../../../../../main/webapp/app/entities/grv-item-person-grv/grv-item-person-grv.model';
import { GrvItemGrvService } from '../../../../../../main/webapp/app/entities/grv-item-grv';

describe('Component Tests', () => {

    describe('GrvItemPersonGrv Management Dialog Component', () => {
        let comp: GrvItemPersonGrvDialogComponent;
        let fixture: ComponentFixture<GrvItemPersonGrvDialogComponent>;
        let service: GrvItemPersonGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [GrvItemPersonGrvDialogComponent],
                providers: [
                    GrvItemGrvService,
                    GrvItemPersonGrvService
                ]
            })
            .overrideTemplate(GrvItemPersonGrvDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrvItemPersonGrvDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrvItemPersonGrvService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new GrvItemPersonGrv(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.grvItemPerson = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'grvItemPersonListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new GrvItemPersonGrv();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.grvItemPerson = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'grvItemPersonListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
