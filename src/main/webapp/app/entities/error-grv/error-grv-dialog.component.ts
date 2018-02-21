import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ErrorGrv } from './error-grv.model';
import { ErrorGrvPopupService } from './error-grv-popup.service';
import { ErrorGrvService } from './error-grv.service';
import { SourceGrv, SourceGrvService } from '../source-grv';
import { GrvItemGrv, GrvItemGrvService } from '../grv-item-grv';

@Component({
    selector: 'jhi-error-grv-dialog',
    templateUrl: './error-grv-dialog.component.html'
})
export class ErrorGrvDialogComponent implements OnInit {

    error: ErrorGrv;
    isSaving: boolean;

    sources: SourceGrv[];

    grvitems: GrvItemGrv[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private errorService: ErrorGrvService,
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
        if (this.error.id !== undefined) {
            this.subscribeToSaveResponse(
                this.errorService.update(this.error));
        } else {
            this.subscribeToSaveResponse(
                this.errorService.create(this.error));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ErrorGrv>>) {
        result.subscribe((res: HttpResponse<ErrorGrv>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ErrorGrv) {
        this.eventManager.broadcast({ name: 'errorListModification', content: 'OK'});
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
    selector: 'jhi-error-grv-popup',
    template: ''
})
export class ErrorGrvPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private errorPopupService: ErrorGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.errorPopupService
                    .open(ErrorGrvDialogComponent as Component, params['id']);
            } else {
                this.errorPopupService
                    .open(ErrorGrvDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
