import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiDataUtils, JhiEventManager} from 'ng-jhipster';

import {ParseErrorGrv} from './parse-error-grv.model';
import {ParseErrorGrvPopupService} from './parse-error-grv-popup.service';
import {ParseErrorGrvService} from './parse-error-grv.service';
import {SourceGrv, SourceGrvService} from '../source-grv';
import {GrvItemGrv, GrvItemGrvService} from '../grv-item-grv';

@Component({
    selector: 'jhi-parse-error-grv-dialog',
    templateUrl: './parse-error-grv-dialog.component.html'
})
export class ParseErrorGrvDialogComponent implements OnInit {

    parseError: ParseErrorGrv;
    isSaving: boolean;

    sources: SourceGrv[];

    grvitems: GrvItemGrv[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private parseErrorService: ParseErrorGrvService,
        private sourceService: SourceGrvService,
        private grvItemService: GrvItemGrvService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.sourceService.query()
            .subscribe((res: HttpResponse<SourceGrv[]>) => { this.sources = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.grvItemService.query()
            .subscribe((res: HttpResponse<GrvItemGrv[]>) => { this.grvitems = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.parseError.id !== undefined) {
            this.subscribeToSaveResponse(
                this.parseErrorService.update(this.parseError));
        } else {
            this.subscribeToSaveResponse(
                this.parseErrorService.create(this.parseError));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ParseErrorGrv>>) {
        result.subscribe((res: HttpResponse<ParseErrorGrv>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ParseErrorGrv) {
        this.eventManager.broadcast({ name: 'parseErrorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSourceById(index: number, item: SourceGrv) {
        return item.id;
    }

    trackGrvItemById(index: number, item: GrvItemGrv) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-parse-error-grv-popup',
    template: ''
})
export class ParseErrorGrvPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parseErrorPopupService: ParseErrorGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.parseErrorPopupService
                    .open(ParseErrorGrvDialogComponent as Component, params['id']);
            } else {
                this.parseErrorPopupService
                    .open(ParseErrorGrvDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
