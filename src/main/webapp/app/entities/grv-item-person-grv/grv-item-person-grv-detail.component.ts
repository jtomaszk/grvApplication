import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs/Subscription';
import {JhiEventManager} from 'ng-jhipster';

import {GrvItemPersonGrv} from './grv-item-person-grv.model';
import {GrvItemPersonGrvService} from './grv-item-person-grv.service';

@Component({
    selector: 'jhi-grv-item-person-grv-detail',
    templateUrl: './grv-item-person-grv-detail.component.html'
})
export class GrvItemPersonGrvDetailComponent implements OnInit, OnDestroy {

    grvItemPerson: GrvItemPersonGrv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private grvItemPersonService: GrvItemPersonGrvService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGrvItemPeople();
    }

    load(id) {
        this.grvItemPersonService.find(id)
            .subscribe((grvItemPersonResponse: HttpResponse<GrvItemPersonGrv>) => {
                this.grvItemPerson = grvItemPersonResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGrvItemPeople() {
        this.eventSubscriber = this.eventManager.subscribe(
            'grvItemPersonListModification',
            (response) => this.load(this.grvItemPerson.id)
        );
    }
}
