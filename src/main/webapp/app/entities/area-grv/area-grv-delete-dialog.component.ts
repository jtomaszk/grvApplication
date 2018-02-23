import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {AreaGrv} from './area-grv.model';
import {AreaGrvPopupService} from './area-grv-popup.service';
import {AreaGrvService} from './area-grv.service';

@Component({
    selector: 'jhi-area-grv-delete-dialog',
    templateUrl: './area-grv-delete-dialog.component.html'
})
export class AreaGrvDeleteDialogComponent {

    area: AreaGrv;

    constructor(
        private areaService: AreaGrvService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.areaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'areaListModification',
                content: 'Deleted an area'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-area-grv-delete-popup',
    template: ''
})
export class AreaGrvDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private areaPopupService: AreaGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.areaPopupService
                .open(AreaGrvDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
