import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AreaGrv } from './area-grv.model';
import { AreaGrvService } from './area-grv.service';

@Component({
    selector: 'jhi-area-grv-detail',
    templateUrl: './area-grv-detail.component.html'
})
export class AreaGrvDetailComponent implements OnInit, OnDestroy {

    area: AreaGrv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private areaService: AreaGrvService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAreas();
    }

    load(id) {
        this.areaService.find(id)
            .subscribe((areaResponse: HttpResponse<AreaGrv>) => {
                this.area = areaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAreas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'areaListModification',
            (response) => this.load(this.area.id)
        );
    }
}
