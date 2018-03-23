import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';
import {JhiEventManager} from 'ng-jhipster';

import {SourceGrv} from './source-grv.model';
import {SourceGrvService} from './source-grv.service';

@Component({
    selector: 'jhi-source-grv-detail',
    templateUrl: './source-grv-detail.component.html'
})
export class SourceGrvDetailComponent implements OnInit, OnDestroy {

    source: SourceGrv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sourceService: SourceGrvService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSources();
    }

    load(id) {
        this.sourceService.find(id)
            .subscribe((sourceResponse: HttpResponse<SourceGrv>) => {
                this.source = sourceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSources() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sourceListModification',
            (response) => this.load(this.source.id)
        );
    }
}
