import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { LocationImageGrv } from './location-image-grv.model';
import { LocationImageGrvService } from './location-image-grv.service';

@Component({
    selector: 'jhi-location-image-grv-detail',
    templateUrl: './location-image-grv-detail.component.html'
})
export class LocationImageGrvDetailComponent implements OnInit, OnDestroy {

    locationImage: LocationImageGrv;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private locationImageService: LocationImageGrvService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLocationImages();
    }

    load(id) {
        this.locationImageService.find(id)
            .subscribe((locationImageResponse: HttpResponse<LocationImageGrv>) => {
                this.locationImage = locationImageResponse.body;
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

    registerChangeInLocationImages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'locationImageListModification',
            (response) => this.load(this.locationImage.id)
        );
    }
}
