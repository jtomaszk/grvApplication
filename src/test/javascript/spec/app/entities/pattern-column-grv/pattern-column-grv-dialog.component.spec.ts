/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { PatternColumnGrvDialogComponent } from '../../../../../../main/webapp/app/entities/pattern-column-grv/pattern-column-grv-dialog.component';
import { PatternColumnGrvService } from '../../../../../../main/webapp/app/entities/pattern-column-grv/pattern-column-grv.service';
import { PatternColumnGrv } from '../../../../../../main/webapp/app/entities/pattern-column-grv/pattern-column-grv.model';
import { PatternGrvService } from '../../../../../../main/webapp/app/entities/pattern-grv';

describe('Component Tests', () => {

    describe('PatternColumnGrv Management Dialog Component', () => {
        let comp: PatternColumnGrvDialogComponent;
        let fixture: ComponentFixture<PatternColumnGrvDialogComponent>;
        let service: PatternColumnGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [PatternColumnGrvDialogComponent],
                providers: [
                    PatternGrvService,
                    PatternColumnGrvService
                ]
            })
            .overrideTemplate(PatternColumnGrvDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PatternColumnGrvDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatternColumnGrvService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PatternColumnGrv(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.patternColumn = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'patternColumnListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PatternColumnGrv();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.patternColumn = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'patternColumnListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
