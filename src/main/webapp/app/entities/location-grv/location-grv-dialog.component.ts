import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {LocationGrv} from './location-grv.model';
import {LocationGrvPopupService} from './location-grv-popup.service';
import {LocationGrvService} from './location-grv.service';
import {SourceGrv, SourceGrvService} from '../source-grv';

@Component({
    selector: 'jhi-location-grv-dialog',
    templateUrl: './location-grv-dialog.component.html'
})
export class LocationGrvDialogComponent implements OnInit {

    location: LocationGrv;
    isSaving: boolean;

    sources: SourceGrv[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private locationService: LocationGrvService,
        private sourceService: SourceGrvService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.sourceService.query()
            .subscribe((res: HttpResponse<SourceGrv[]>) => { this.sources = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.location.id !== undefined) {
            this.subscribeToSaveResponse(
                this.locationService.update(this.location));
        } else {
            this.subscribeToSaveResponse(
                this.locationService.create(this.location));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LocationGrv>>) {
        result.subscribe((res: HttpResponse<LocationGrv>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LocationGrv) {
        this.eventManager.broadcast({ name: 'locationListModification', content: 'OK'});
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
    selector: 'jhi-location-grv-popup',
    template: ''
})
export class LocationGrvPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private locationPopupService: LocationGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.locationPopupService
                    .open(LocationGrvDialogComponent as Component, params['id']);
            } else {
                this.locationPopupService
                    .open(LocationGrvDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
