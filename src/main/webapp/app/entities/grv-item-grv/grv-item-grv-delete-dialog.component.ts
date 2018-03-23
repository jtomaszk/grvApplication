import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {GrvItemGrv} from './grv-item-grv.model';
import {GrvItemGrvPopupService} from './grv-item-grv-popup.service';
import {GrvItemGrvService} from './grv-item-grv.service';

@Component({
    selector: 'jhi-grv-item-grv-delete-dialog',
    templateUrl: './grv-item-grv-delete-dialog.component.html'
})
export class GrvItemGrvDeleteDialogComponent {

    grvItem: GrvItemGrv;

    constructor(
        private grvItemService: GrvItemGrvService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.grvItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'grvItemListModification',
                content: 'Deleted an grvItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-grv-item-grv-delete-popup',
    template: ''
})
export class GrvItemGrvDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private grvItemPopupService: GrvItemGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.grvItemPopupService
                .open(GrvItemGrvDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
