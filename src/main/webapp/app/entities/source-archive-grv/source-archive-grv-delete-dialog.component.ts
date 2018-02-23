import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {SourceArchiveGrv} from './source-archive-grv.model';
import {SourceArchiveGrvPopupService} from './source-archive-grv-popup.service';
import {SourceArchiveGrvService} from './source-archive-grv.service';

@Component({
    selector: 'jhi-source-archive-grv-delete-dialog',
    templateUrl: './source-archive-grv-delete-dialog.component.html'
})
export class SourceArchiveGrvDeleteDialogComponent {

    sourceArchive: SourceArchiveGrv;

    constructor(
        private sourceArchiveService: SourceArchiveGrvService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sourceArchiveService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sourceArchiveListModification',
                content: 'Deleted an sourceArchive'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-source-archive-grv-delete-popup',
    template: ''
})
export class SourceArchiveGrvDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sourceArchivePopupService: SourceArchiveGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sourceArchivePopupService
                .open(SourceArchiveGrvDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
