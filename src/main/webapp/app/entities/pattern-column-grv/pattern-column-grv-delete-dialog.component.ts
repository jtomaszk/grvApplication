import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {PatternColumnGrv} from './pattern-column-grv.model';
import {PatternColumnGrvPopupService} from './pattern-column-grv-popup.service';
import {PatternColumnGrvService} from './pattern-column-grv.service';

@Component({
    selector: 'jhi-pattern-column-grv-delete-dialog',
    templateUrl: './pattern-column-grv-delete-dialog.component.html'
})
export class PatternColumnGrvDeleteDialogComponent {

    patternColumn: PatternColumnGrv;

    constructor(
        private patternColumnService: PatternColumnGrvService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.patternColumnService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'patternColumnListModification',
                content: 'Deleted an patternColumn'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pattern-column-grv-delete-popup',
    template: ''
})
export class PatternColumnGrvDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private patternColumnPopupService: PatternColumnGrvPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.patternColumnPopupService
                .open(PatternColumnGrvDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
