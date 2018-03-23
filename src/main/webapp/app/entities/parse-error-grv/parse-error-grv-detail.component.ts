import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';
import {JhiDataUtils, JhiEventManager} from 'ng-jhipster';

import {ParseErrorGrv} from './parse-error-grv.model';
import {ParseErrorGrvService} from './parse-error-grv.service';

@Component({
    selector: 'jhi-parse-error-grv-detail',
    templateUrl: './parse-error-grv-detail.component.html'
})
export class ParseErrorGrvDetailComponent implements OnInit, OnDestroy {

    parseError: ParseErrorGrv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private parseErrorService: ParseErrorGrvService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInParseErrors();
    }

    load(id) {
        this.parseErrorService.find(id)
            .subscribe((parseErrorResponse: HttpResponse<ParseErrorGrv>) => {
                this.parseError = parseErrorResponse.body;
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

    registerChangeInParseErrors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'parseErrorListModification',
            (response) => this.load(this.parseError.id)
        );
    }
}
