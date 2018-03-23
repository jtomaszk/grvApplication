import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {SourceGrv} from './source-grv.model';
import {SourceGrvPopupService} from './source-grv-popup.service';
import {SourceGrvService} from './source-grv.service';
import {AreaGrv, AreaGrvService} from '../area-grv';
import {InputPatternGrv, InputPatternGrvService} from '../input-pattern-grv';

@Component({
    selector: 'jhi-source-grv-dialog',
    templateUrl: './source-grv-dialog.component.html'
})
export class SourceGrvDialogComponent implements OnInit {

    source: SourceGrv;
    isSaving: boolean;

    areas: AreaGrv[];

    inputpatterns: InputPatternGrv[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private sourceService: SourceGrvService,
        private areaService: AreaGrvService,
        private inputPatternService: InputPatternGrvService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.areaService.query()
            .subscribe((res: HttpResponse<AreaGrv[]>) => { this.areas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.inputPatternService.query()
            .subscribe((res: HttpResponse<InputPatternGrv[]>) => { this.inputpatterns = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.source.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sourceService.update(this.source));
        } else {
            this.subscribeToSaveResponse(
                this.sourceService.create(this.source));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SourceGrv>>) {
        result.subscribe((res: HttpResponse<SourceGrv>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SourceGrv) {
        this.eventManager.broadcast({ name: 'sourceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAreaById(index: number, item: AreaGrv) {
        return item.id;
    }

    trackInputPatternById(index: number, item: InputPatternGrv) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-source-grv-popup',
    template: ''
})
export class SourceGrvPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sourcePopupService: SourceGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sourcePopupService
                    .open(SourceGrvDialogComponent as Component, params['id']);
            } else {
                this.sourcePopupService
                    .open(SourceGrvDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
