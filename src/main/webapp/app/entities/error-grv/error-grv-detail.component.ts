import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';
import {JhiDataUtils, JhiEventManager} from 'ng-jhipster';

import {ErrorGrv} from './error-grv.model';
import {ErrorGrvService} from './error-grv.service';

@Component({
    selector: 'jhi-error-grv-detail',
    templateUrl: './error-grv-detail.component.html'
})
export class ErrorGrvDetailComponent implements OnInit, OnDestroy {

    error: ErrorGrv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private errorService: ErrorGrvService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInErrors();
    }

    load(id) {
        this.errorService.find(id)
            .subscribe((errorResponse: HttpResponse<ErrorGrv>) => {
                this.error = errorResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInErrors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'errorListModification',
            (response) => this.load(this.error.id)
        );
    }
}
