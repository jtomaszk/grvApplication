import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiDataUtils, JhiEventManager} from 'ng-jhipster';

import {SourceArchiveGrv} from './source-archive-grv.model';
import {SourceArchiveGrvPopupService} from './source-archive-grv-popup.service';
import {SourceArchiveGrvService} from './source-archive-grv.service';
import {SourceGrv, SourceGrvService} from '../source-grv';

@Component({
    selector: 'jhi-source-archive-grv-dialog',
    templateUrl: './source-archive-grv-dialog.component.html'
})
export class SourceArchiveGrvDialogComponent implements OnInit {

    sourceArchive: SourceArchiveGrv;
    isSaving: boolean;

    sources: SourceGrv[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private sourceArchiveService: SourceArchiveGrvService,
        private sourceService: SourceGrvService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.sourceService.query()
            .subscribe((res: HttpResponse<SourceGrv[]>) => { this.sources = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.sourceArchive.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sourceArchiveService.update(this.sourceArchive));
        } else {
            this.subscribeToSaveResponse(
                this.sourceArchiveService.create(this.sourceArchive));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SourceArchiveGrv>>) {
        result.subscribe((res: HttpResponse<SourceArchiveGrv>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SourceArchiveGrv) {
        this.eventManager.broadcast({ name: 'sourceArchiveListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-source-archive-grv-popup',
    template: ''
})
export class SourceArchiveGrvPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sourceArchivePopupService: SourceArchiveGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sourceArchivePopupService
                    .open(SourceArchiveGrvDialogComponent as Component, params['id']);
            } else {
                this.sourceArchivePopupService
                    .open(SourceArchiveGrvDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
