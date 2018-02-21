/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { GrvItemGrvDialogComponent } from '../../../../../../main/webapp/app/entities/grv-item-grv/grv-item-grv-dialog.component';
import { GrvItemGrvService } from '../../../../../../main/webapp/app/entities/grv-item-grv/grv-item-grv.service';
import { GrvItemGrv } from '../../../../../../main/webapp/app/entities/grv-item-grv/grv-item-grv.model';
import { SourceGrvService } from '../../../../../../main/webapp/app/entities/source-grv';

describe('Component Tests', () => {

    describe('GrvItemGrv Management Dialog Component', () => {
        let comp: GrvItemGrvDialogComponent;
        let fixture: ComponentFixture<GrvItemGrvDialogComponent>;
        let service: GrvItemGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [GrvItemGrvDialogComponent],
                providers: [
                    SourceGrvService,
                    GrvItemGrvService
                ]
            })
            .overrideTemplate(GrvItemGrvDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrvItemGrvDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrvItemGrvService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new GrvItemGrv(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.grvItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'grvItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new GrvItemGrv();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.grvItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'grvItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
