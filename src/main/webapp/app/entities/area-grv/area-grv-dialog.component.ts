import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AreaGrv } from './area-grv.model';
import { AreaGrvPopupService } from './area-grv-popup.service';
import { AreaGrvService } from './area-grv.service';

@Component({
    selector: 'jhi-area-grv-dialog',
    templateUrl: './area-grv-dialog.component.html'
})
export class AreaGrvDialogComponent implements OnInit {

    area: AreaGrv;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private areaService: AreaGrvService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.area.id !== undefined) {
            this.subscribeToSaveResponse(
                this.areaService.update(this.area));
        } else {
            this.subscribeToSaveResponse(
                this.areaService.create(this.area));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AreaGrv>>) {
        result.subscribe((res: HttpResponse<AreaGrv>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AreaGrv) {
        this.eventManager.broadcast({ name: 'areaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-area-grv-popup',
    template: ''
})
export class AreaGrvPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private areaPopupService: AreaGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.areaPopupService
                    .open(AreaGrvDialogComponent as Component, params['id']);
            } else {
                this.areaPopupService
                    .open(AreaGrvDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
