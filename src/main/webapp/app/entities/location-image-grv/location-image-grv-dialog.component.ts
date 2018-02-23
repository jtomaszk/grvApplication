import {Component, ElementRef, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiDataUtils, JhiEventManager} from 'ng-jhipster';

import {LocationImageGrv} from './location-image-grv.model';
import {LocationImageGrvPopupService} from './location-image-grv-popup.service';
import {LocationImageGrvService} from './location-image-grv.service';
import {LocationGrv, LocationGrvService} from '../location-grv';

@Component({
    selector: 'jhi-location-image-grv-dialog',
    templateUrl: './location-image-grv-dialog.component.html'
})
export class LocationImageGrvDialogComponent implements OnInit {

    locationImage: LocationImageGrv;
    isSaving: boolean;

    locations: LocationGrv[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private locationImageService: LocationImageGrvService,
        private locationService: LocationGrvService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.locationService.query()
            .subscribe((res: HttpResponse<LocationGrv[]>) => { this.locations = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.locationImage, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.locationImage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.locationImageService.update(this.locationImage));
        } else {
            this.subscribeToSaveResponse(
                this.locationImageService.create(this.locationImage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LocationImageGrv>>) {
        result.subscribe((res: HttpResponse<LocationImageGrv>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LocationImageGrv) {
        this.eventManager.broadcast({ name: 'locationImageListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLocationById(index: number, item: LocationGrv) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-location-image-grv-popup',
    template: ''
})
export class LocationImageGrvPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private locationImagePopupService: LocationImageGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.locationImagePopupService
                    .open(LocationImageGrvDialogComponent as Component, params['id']);
            } else {
                this.locationImagePopupService
                    .open(LocationImageGrvDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
