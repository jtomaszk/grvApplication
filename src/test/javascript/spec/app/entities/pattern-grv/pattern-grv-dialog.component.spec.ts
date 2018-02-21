/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { PatternGrvDialogComponent } from '../../../../../../main/webapp/app/entities/pattern-grv/pattern-grv-dialog.component';
import { PatternGrvService } from '../../../../../../main/webapp/app/entities/pattern-grv/pattern-grv.service';
import { PatternGrv } from '../../../../../../main/webapp/app/entities/pattern-grv/pattern-grv.model';

describe('Component Tests', () => {

    describe('PatternGrv Management Dialog Component', () => {
        let comp: PatternGrvDialogComponent;
        let fixture: ComponentFixture<PatternGrvDialogComponent>;
        let service: PatternGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [PatternGrvDialogComponent],
                providers: [
                    PatternGrvService
                ]
            })
            .overrideTemplate(PatternGrvDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PatternGrvDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatternGrvService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PatternGrv(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.pattern = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'patternListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PatternGrv();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.pattern = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'patternListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
