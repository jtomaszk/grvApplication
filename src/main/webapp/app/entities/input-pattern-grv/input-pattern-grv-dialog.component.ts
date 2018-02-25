import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InputPatternGrv } from './input-pattern-grv.model';
import { InputPatternGrvPopupService } from './input-pattern-grv-popup.service';
import { InputPatternGrvService } from './input-pattern-grv.service';

@Component({
    selector: 'jhi-input-pattern-grv-dialog',
    templateUrl: './input-pattern-grv-dialog.component.html'
})
export class InputPatternGrvDialogComponent implements OnInit {

    inputPattern: InputPatternGrv;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private inputPatternService: InputPatternGrvService,
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
        if (this.inputPattern.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inputPatternService.update(this.inputPattern));
        } else {
            this.subscribeToSaveResponse(
                this.inputPatternService.create(this.inputPattern));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<InputPatternGrv>>) {
        result.subscribe((res: HttpResponse<InputPatternGrv>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: InputPatternGrv) {
        this.eventManager.broadcast({ name: 'inputPatternListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-input-pattern-grv-popup',
    template: ''
})
export class InputPatternGrvPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inputPatternPopupService: InputPatternGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.inputPatternPopupService
                    .open(InputPatternGrvDialogComponent as Component, params['id']);
            } else {
                this.inputPatternPopupService
                    .open(InputPatternGrvDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
