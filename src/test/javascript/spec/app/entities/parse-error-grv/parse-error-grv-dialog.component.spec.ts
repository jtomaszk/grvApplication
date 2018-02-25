/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GrvApplicationTestModule } from '../../../test.module';
import { ParseErrorGrvDialogComponent } from '../../../../../../main/webapp/app/entities/parse-error-grv/parse-error-grv-dialog.component';
import { ParseErrorGrvService } from '../../../../../../main/webapp/app/entities/parse-error-grv/parse-error-grv.service';
import { ParseErrorGrv } from '../../../../../../main/webapp/app/entities/parse-error-grv/parse-error-grv.model';
import { SourceGrvService } from '../../../../../../main/webapp/app/entities/source-grv';
import { GrvItemGrvService } from '../../../../../../main/webapp/app/entities/grv-item-grv';

describe('Component Tests', () => {

    describe('ParseErrorGrv Management Dialog Component', () => {
        let comp: ParseErrorGrvDialogComponent;
        let fixture: ComponentFixture<ParseErrorGrvDialogComponent>;
        let service: ParseErrorGrvService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GrvApplicationTestModule],
                declarations: [ParseErrorGrvDialogComponent],
                providers: [
                    SourceGrvService,
                    GrvItemGrvService,
                    ParseErrorGrvService
                ]
            })
            .overrideTemplate(ParseErrorGrvDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParseErrorGrvDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParseErrorGrvService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ParseErrorGrv(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.parseError = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'parseErrorListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ParseErrorGrv();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.parseError = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'parseErrorListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
