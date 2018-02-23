import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiDataUtils, JhiEventManager} from 'ng-jhipster';

import {GrvItemGrv} from './grv-item-grv.model';
import {GrvItemGrvPopupService} from './grv-item-grv-popup.service';
import {GrvItemGrvService} from './grv-item-grv.service';
import {SourceGrv, SourceGrvService} from '../source-grv';
import {LocationGrv, LocationGrvService} from '../location-grv';
import {SourceArchiveGrv, SourceArchiveGrvService} from '../source-archive-grv';
import {GrvItemPersonGrv, GrvItemPersonGrvService} from '../grv-item-person-grv';

@Component({
    selector: 'jhi-grv-item-grv-dialog',
    templateUrl: './grv-item-grv-dialog.component.html'
})
export class GrvItemGrvDialogComponent implements OnInit {

    grvItem: GrvItemGrv;
    isSaving: boolean;

    sources: SourceGrv[];

    locations: LocationGrv[];

    sourcearchives: SourceArchiveGrv[];

    grvitempeople: GrvItemPersonGrv[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private grvItemService: GrvItemGrvService,
        private sourceService: SourceGrvService,
        private locationService: LocationGrvService,
        private sourceArchiveService: SourceArchiveGrvService,
        private grvItemPersonService: GrvItemPersonGrvService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.sourceService.query()
            .subscribe((res: HttpResponse<SourceGrv[]>) => { this.sources = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.locationService.query()
            .subscribe((res: HttpResponse<LocationGrv[]>) => { this.locations = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.sourceArchiveService.query()
            .subscribe((res: HttpResponse<SourceArchiveGrv[]>) => { this.sourcearchives = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.grvItemPersonService.query()
            .subscribe((res: HttpResponse<GrvItemPersonGrv[]>) => { this.grvitempeople = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.grvItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.grvItemService.update(this.grvItem));
        } else {
            this.subscribeToSaveResponse(
                this.grvItemService.create(this.grvItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<GrvItemGrv>>) {
        result.subscribe((res: HttpResponse<GrvItemGrv>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: GrvItemGrv) {
        this.eventManager.broadcast({ name: 'grvItemListModification', content: 'OK'});
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

    trackLocationById(index: number, item: LocationGrv) {
        return item.id;
    }

    trackSourceArchiveById(index: number, item: SourceArchiveGrv) {
        return item.id;
    }

    trackGrvItemPersonById(index: number, item: GrvItemPersonGrv) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-grv-item-grv-popup',
    template: ''
})
export class GrvItemGrvPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private grvItemPopupService: GrvItemGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.grvItemPopupService
                    .open(GrvItemGrvDialogComponent as Component, params['id']);
            } else {
                this.grvItemPopupService
                    .open(GrvItemGrvDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
