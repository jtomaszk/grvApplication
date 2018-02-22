import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GrvItemPersonGrv } from './grv-item-person-grv.model';
import { GrvItemPersonGrvPopupService } from './grv-item-person-grv-popup.service';
import { GrvItemPersonGrvService } from './grv-item-person-grv.service';

@Component({
    selector: 'jhi-grv-item-person-grv-delete-dialog',
    templateUrl: './grv-item-person-grv-delete-dialog.component.html'
})
export class GrvItemPersonGrvDeleteDialogComponent {

    grvItemPerson: GrvItemPersonGrv;

    constructor(
        private grvItemPersonService: GrvItemPersonGrvService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.grvItemPersonService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'grvItemPersonListModification',
                content: 'Deleted an grvItemPerson'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-grv-item-person-grv-delete-popup',
    template: ''
})
export class GrvItemPersonGrvDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private grvItemPersonPopupService: GrvItemPersonGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.grvItemPersonPopupService
                .open(GrvItemPersonGrvDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
