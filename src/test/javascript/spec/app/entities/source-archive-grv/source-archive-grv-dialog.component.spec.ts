/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { SourceArchiveGrvDialogComponent } from '../../../../../../main/webapp/app/entities/source-archive-grv/source-archive-grv-dialog.component';
import { SourceArchiveGrvService } from '../../../../../../main/webapp/app/entities/source-archive-grv/source-archive-grv.service';
import { SourceArchiveGrv } from '../../../../../../main/webapp/app/entities/source-archive-grv/source-archive-grv.model';
import { SourceGrvService } from '../../../../../../main/webapp/app/entities/source-grv';

describe('Component Tests', () => {

    describe('SourceArchiveGrv Management Dialog Component', () => {
        let comp: SourceArchiveGrvDialogComponent;
        let fixture: ComponentFixture<SourceArchiveGrvDialogComponent>;
        let service: SourceArchiveGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [SourceArchiveGrvDialogComponent],
                providers: [
                    SourceGrvService,
                    SourceArchiveGrvService
                ]
            })
            .overrideTemplate(SourceArchiveGrvDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SourceArchiveGrvDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceArchiveGrvService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SourceArchiveGrv(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.sourceArchive = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sourceArchiveListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SourceArchiveGrv();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.sourceArchive = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sourceArchiveListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
