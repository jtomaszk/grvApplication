import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PatternGrv } from './pattern-grv.model';
import { PatternGrvPopupService } from './pattern-grv-popup.service';
import { PatternGrvService } from './pattern-grv.service';

@Component({
    selector: 'jhi-pattern-grv-dialog',
    templateUrl: './pattern-grv-dialog.component.html'
})
export class PatternGrvDialogComponent implements OnInit {

    pattern: PatternGrv;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private patternService: PatternGrvService,
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
        if (this.pattern.id !== undefined) {
            this.subscribeToSaveResponse(
                this.patternService.update(this.pattern));
        } else {
            this.subscribeToSaveResponse(
                this.patternService.create(this.pattern));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PatternGrv>>) {
        result.subscribe((res: HttpResponse<PatternGrv>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PatternGrv) {
        this.eventManager.broadcast({ name: 'patternListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-pattern-grv-popup',
    template: ''
})
export class PatternGrvPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private patternPopupService: PatternGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.patternPopupService
                    .open(PatternGrvDialogComponent as Component, params['id']);
            } else {
                this.patternPopupService
                    .open(PatternGrvDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
