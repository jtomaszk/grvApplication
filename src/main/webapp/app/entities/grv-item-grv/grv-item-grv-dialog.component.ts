import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { GrvItemGrv } from './grv-item-grv.model';
import { GrvItemGrvPopupService } from './grv-item-grv-popup.service';
import { GrvItemGrvService } from './grv-item-grv.service';
import { SourceGrv, SourceGrvService } from '../source-grv';

@Component({
    selector: 'jhi-grv-item-grv-dialog',
    templateUrl: './grv-item-grv-dialog.component.html'
})
export class GrvItemGrvDialogComponent implements OnInit {

    grvItem: GrvItemGrv;
    isSaving: boolean;

    sources: SourceGrv[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private grvItemService: GrvItemGrvService,
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
