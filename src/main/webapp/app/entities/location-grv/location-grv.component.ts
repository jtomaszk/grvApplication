import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LocationGrv } from './location-grv.model';
import { LocationGrvService } from './location-grv.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-location-grv',
    templateUrl: './location-grv.component.html'
})
export class LocationGrvComponent implements OnInit, OnDestroy {
locations: LocationGrv[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private locationService: LocationGrvService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.locationService.query().subscribe(
            (res: HttpResponse<LocationGrv[]>) => {
                this.locations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLocations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LocationGrv) {
        return item.id;
    }
    registerChangeInLocations() {
        this.eventSubscriber = this.eventManager.subscribe('locationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
