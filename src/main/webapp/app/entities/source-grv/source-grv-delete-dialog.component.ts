import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SourceGrv } from './source-grv.model';
import { SourceGrvPopupService } from './source-grv-popup.service';
import { SourceGrvService } from './source-grv.service';

@Component({
    selector: 'jhi-source-grv-delete-dialog',
    templateUrl: './source-grv-delete-dialog.component.html'
})
export class SourceGrvDeleteDialogComponent {

    source: SourceGrv;

    constructor(
        private sourceService: SourceGrvService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sourceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sourceListModification',
                content: 'Deleted an source'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-source-grv-delete-popup',
    template: ''
})
export class SourceGrvDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sourcePopupService: SourceGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sourcePopupService
                .open(SourceGrvDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
