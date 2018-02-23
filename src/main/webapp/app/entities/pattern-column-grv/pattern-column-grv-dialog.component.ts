import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {PatternColumnGrv} from './pattern-column-grv.model';
import {PatternColumnGrvPopupService} from './pattern-column-grv-popup.service';
import {PatternColumnGrvService} from './pattern-column-grv.service';
import {InputPatternGrv, InputPatternGrvService} from '../input-pattern-grv';

@Component({
    selector: 'jhi-pattern-column-grv-dialog',
    templateUrl: './pattern-column-grv-dialog.component.html'
})
export class PatternColumnGrvDialogComponent implements OnInit {

    patternColumn: PatternColumnGrv;
    isSaving: boolean;

    inputpatterns: InputPatternGrv[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private patternColumnService: PatternColumnGrvService,
        private inputPatternService: InputPatternGrvService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inputPatternService.query()
            .subscribe((res: HttpResponse<InputPatternGrv[]>) => { this.inputpatterns = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.patternColumn.id !== undefined) {
            this.subscribeToSaveResponse(
                this.patternColumnService.update(this.patternColumn));
        } else {
            this.subscribeToSaveResponse(
                this.patternColumnService.create(this.patternColumn));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PatternColumnGrv>>) {
        result.subscribe((res: HttpResponse<PatternColumnGrv>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PatternColumnGrv) {
        this.eventManager.broadcast({ name: 'patternColumnListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackInputPatternById(index: number, item: InputPatternGrv) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pattern-column-grv-popup',
    template: ''
})
export class PatternColumnGrvPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private patternColumnPopupService: PatternColumnGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.patternColumnPopupService
                    .open(PatternColumnGrvDialogComponent as Component, params['id']);
            } else {
                this.patternColumnPopupService
                    .open(PatternColumnGrvDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
