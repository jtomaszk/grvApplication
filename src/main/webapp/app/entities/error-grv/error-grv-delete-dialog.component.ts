import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ErrorGrv } from './error-grv.model';
import { ErrorGrvPopupService } from './error-grv-popup.service';
import { ErrorGrvService } from './error-grv.service';

@Component({
    selector: 'jhi-error-grv-delete-dialog',
    templateUrl: './error-grv-delete-dialog.component.html'
})
export class ErrorGrvDeleteDialogComponent {

    error: ErrorGrv;

    constructor(
        private errorService: ErrorGrvService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.errorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'errorListModification',
                content: 'Deleted an error'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-error-grv-delete-popup',
    template: ''
})
export class ErrorGrvDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private errorPopupService: ErrorGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.errorPopupService
                .open(ErrorGrvDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
