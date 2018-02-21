import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PatternGrv } from './pattern-grv.model';
import { PatternGrvService } from './pattern-grv.service';

@Component({
    selector: 'jhi-pattern-grv-detail',
    templateUrl: './pattern-grv-detail.component.html'
})
export class PatternGrvDetailComponent implements OnInit, OnDestroy {

    pattern: PatternGrv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private patternService: PatternGrvService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPatterns();
    }

    load(id) {
        this.patternService.find(id)
            .subscribe((patternResponse: HttpResponse<PatternGrv>) => {
                this.pattern = patternResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPatterns() {
        this.eventSubscriber = this.eventManager.subscribe(
            'patternListModification',
            (response) => this.load(this.pattern.id)
        );
    }
}
