import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LocationImageGrv } from './location-image-grv.model';
import { LocationImageGrvPopupService } from './location-image-grv-popup.service';
import { LocationImageGrvService } from './location-image-grv.service';

@Component({
    selector: 'jhi-location-image-grv-delete-dialog',
    templateUrl: './location-image-grv-delete-dialog.component.html'
})
export class LocationImageGrvDeleteDialogComponent {

    locationImage: LocationImageGrv;

    constructor(
        private locationImageService: LocationImageGrvService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.locationImageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'locationImageListModification',
                content: 'Deleted an locationImage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-location-image-grv-delete-popup',
    template: ''
})
export class LocationImageGrvDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private locationImagePopupService: LocationImageGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.locationImagePopupService
                .open(LocationImageGrvDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
