import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';
import {JhiEventManager} from 'ng-jhipster';

import {PatternColumnGrv} from './pattern-column-grv.model';
import {PatternColumnGrvService} from './pattern-column-grv.service';

@Component({
    selector: 'jhi-pattern-column-grv-detail',
    templateUrl: './pattern-column-grv-detail.component.html'
})
export class PatternColumnGrvDetailComponent implements OnInit, OnDestroy {

    patternColumn: PatternColumnGrv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private patternColumnService: PatternColumnGrvService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPatternColumns();
    }

    load(id) {
        this.patternColumnService.find(id)
            .subscribe((patternColumnResponse: HttpResponse<PatternColumnGrv>) => {
                this.patternColumn = patternColumnResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPatternColumns() {
        this.eventSubscriber = this.eventManager.subscribe(
            'patternColumnListModification',
            (response) => this.load(this.patternColumn.id)
        );
    }
}
