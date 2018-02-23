import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';
import {JhiEventManager} from 'ng-jhipster';

import {InputPatternGrv} from './input-pattern-grv.model';
import {InputPatternGrvService} from './input-pattern-grv.service';

@Component({
    selector: 'jhi-input-pattern-grv-detail',
    templateUrl: './input-pattern-grv-detail.component.html'
})
export class InputPatternGrvDetailComponent implements OnInit, OnDestroy {

    inputPattern: InputPatternGrv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private inputPatternService: InputPatternGrvService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInInputPatterns();
    }

    load(id) {
        this.inputPatternService.find(id)
            .subscribe((inputPatternResponse: HttpResponse<InputPatternGrv>) => {
                this.inputPattern = inputPatternResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInInputPatterns() {
        this.eventSubscriber = this.eventManager.subscribe(
            'inputPatternListModification',
            (response) => this.load(this.inputPattern.id)
        );
    }
}
