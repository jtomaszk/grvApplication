import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {GrvItemPersonGrv} from './grv-item-person-grv.model';
import {GrvItemPersonGrvPopupService} from './grv-item-person-grv-popup.service';
import {GrvItemPersonGrvService} from './grv-item-person-grv.service';
import {GrvItemGrv, GrvItemGrvService} from '../grv-item-grv';

@Component({
    selector: 'jhi-grv-item-person-grv-dialog',
    templateUrl: './grv-item-person-grv-dialog.component.html'
})
export class GrvItemPersonGrvDialogComponent implements OnInit {

    grvItemPerson: GrvItemPersonGrv;
    isSaving: boolean;

    items: GrvItemGrv[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private grvItemPersonService: GrvItemPersonGrvService,
        private grvItemService: GrvItemGrvService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.grvItemService
            .query({filter: 'person-is-null'})
            .subscribe((res: HttpResponse<GrvItemGrv[]>) => {
                if (!this.grvItemPerson.itemId) {
                    this.items = res.body;
                } else {
                    this.grvItemService
                        .find(this.grvItemPerson.itemId)
                        .subscribe((subRes: HttpResponse<GrvItemGrv>) => {
                            this.items = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.grvItemPerson.id !== undefined) {
            this.subscribeToSaveResponse(
                this.grvItemPersonService.update(this.grvItemPerson));
        } else {
            this.subscribeToSaveResponse(
                this.grvItemPersonService.create(this.grvItemPerson));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<GrvItemPersonGrv>>) {
        result.subscribe((res: HttpResponse<GrvItemPersonGrv>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: GrvItemPersonGrv) {
        this.eventManager.broadcast({ name: 'grvItemPersonListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackGrvItemById(index: number, item: GrvItemGrv) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-grv-item-person-grv-popup',
    template: ''
})
export class GrvItemPersonGrvPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private grvItemPersonPopupService: GrvItemPersonGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.grvItemPersonPopupService
                    .open(GrvItemPersonGrvDialogComponent as Component, params['id']);
            } else {
                this.grvItemPersonPopupService
                    .open(GrvItemPersonGrvDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
