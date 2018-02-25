import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { GrvItemGrv } from './grv-item-grv.model';
import { GrvItemGrvService } from './grv-item-grv.service';

@Component({
    selector: 'jhi-grv-item-grv-detail',
    templateUrl: './grv-item-grv-detail.component.html'
})
export class GrvItemGrvDetailComponent implements OnInit, OnDestroy {

    grvItem: GrvItemGrv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private grvItemService: GrvItemGrvService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGrvItems();
    }

    load(id) {
        this.grvItemService.find(id)
            .subscribe((grvItemResponse: HttpResponse<GrvItemGrv>) => {
                this.grvItem = grvItemResponse.body;
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

    registerChangeInGrvItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'grvItemListModification',
            (response) => this.load(this.grvItem.id)
        );
    }
}
